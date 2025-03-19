package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.DrinkCatalog;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.DrinkCatalogStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonDrinkCatalogStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.BackgroundImageManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;
import seedu.address.ui.WelcomeScreen;

/**
 * Runs the CafeConnect application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected Ui ui;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing CafeConnect ]===========================");
        super.init();

        loadCustomFonts();
        BackgroundImageManager.preloadBackgroundImages();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        DrinkCatalogStorage drinkCatalogStorage = new JsonDrinkCatalogStorage(userPrefs.getDrinkCatalogFilePath());

        storage = new StorageManager(addressBookStorage, userPrefsStorage, drinkCatalogStorage);

        initLogging(config);
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager(model, storage);
        ui = new UiManager(logic);
    }

    /**
     * Application initialization helper method for testing purposes.
     */
    protected void applicationInit() throws Exception {
        logger.info("Application init called");
    }

    /**
     * Loads custom fonts for the application UI.
     */
    private void loadCustomFonts() {
        try {
            // Load Gotham font family
            loadFontFamily("/fonts/", new String[]{
                "Gotham-Book.otf",
                "GOTHAM-BOLD.TTF",
                "GOTHAM-LIGHT.TTF",
                "GOTHAM-MEDIUM.TTF"
            });
            logger.info("Gotham fonts loaded successfully");
        } catch (Exception e) {
            logger.severe("Failed to load Gotham fonts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads a font family with the given base path and variants.
     */
    private void loadFontFamily(String basePath, String[] fontFiles) {
        for (String fontFile : fontFiles) {
            try {
                String fullPath = basePath + fontFile;
                Font loadedFont = Font.loadFont(getClass().getResourceAsStream(fullPath), 12);

                if (loadedFont == null) {
                    logger.warning("Font could not be loaded: " + fullPath);
                }
            } catch (Exception e) {
                logger.warning("Error loading font: " + fontFile + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting CafeConnect " + MainApp.VERSION);
        WelcomeScreen welcomeScreen = new WelcomeScreen(primaryStage, logic, (UiManager) ui);
        welcomeScreen.show();
    }

    /**
     * Initializes the ModelManager with data from storage or sample data.
     */
    protected Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyAddressBook initialAddressBookData = loadAddressBookData(storage);
        ReadOnlyDrinkCatalog initialDrinkCatalogData = loadDrinkCatalogData(storage);

        return new ModelManager(initialAddressBookData, userPrefs, initialDrinkCatalogData);
    }

    /**
     * Loads AddressBook data from storage, using sample data or an empty AddressBook if loading fails.
     */
    private ReadOnlyAddressBook loadAddressBookData(Storage storage) {
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = storage.readAddressBook();

            if (!addressBookOptional.isPresent()) {
                logger.info("Address book data file not found. Will start with a sample AddressBook");
                return SampleDataUtil.getSampleAddressBook();
            }

            return addressBookOptional.get();
        } catch (DataLoadingException e) {
            logger.warning("Address book data file could not be loaded. "
                    + "Will start with an empty AddressBook: " + StringUtil.getDetails(e));
            return new AddressBook();
        }
    }

    /**
     * Loads DrinkCatalog data from storage, using sample data or an empty DrinkCatalog if loading fails.
     */
    private ReadOnlyDrinkCatalog loadDrinkCatalogData(Storage storage) {
        try {
            Optional<ReadOnlyDrinkCatalog> drinkCatalogOptional = storage.readDrinkCatalog();

            if (!drinkCatalogOptional.isPresent()) {
                logger.info("Drink catalog data file not found. Will start with a sample DrinkCatalog");
                return SampleDataUtil.getSampleDrinkCatalog();
            }

            return drinkCatalogOptional.get();
        } catch (DataLoadingException e) {
            logger.warning("Drink catalog data file could not be loaded. "
                    + "Will start with an empty DrinkCatalog: " + StringUtil.getDetails(e));
            return new DrinkCatalog();
        }
    }

    /**
     * Initializes logging configuration.
     */
    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Initializes configuration, using default or custom config file.
     */
    protected Config initConfig(Path configFilePath) {
        Path configFilePathUsed = configFilePath != null
                ? configFilePath
                : Config.DEFAULT_CONFIG_FILE;

        logger.info("Using config file: " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            Config initializedConfig = configOptional.orElse(new Config());
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
            return initializedConfig;
        } catch (Exception e) {
            logger.warning("Config file could not be read. Using default config: " + StringUtil.getDetails(e));
            return new Config();
        }
    }

    /**
     * Initializes user preferences, using existing or default preferences.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file: " + prefsFilePath);

        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();

            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file: " + prefsFilePath);
            }

            UserPrefs initializedPrefs = prefsOptional.orElse(new UserPrefs());
            storage.saveUserPrefs(initializedPrefs);
            return initializedPrefs;
        } catch (Exception e) {
            logger.warning("UserPrefs file could not be read. Using default prefs: " + StringUtil.getDetails(e));
            return new UserPrefs();
        }
    }

    @Override
    public void stop() {
        logger.info("============================ Stopping CafeConnect =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences: " + StringUtil.getDetails(e));
        }
    }
}
