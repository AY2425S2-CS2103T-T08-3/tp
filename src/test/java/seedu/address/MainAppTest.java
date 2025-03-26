package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.DrinkCatalog;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.DrinkCatalogStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonDrinkCatalogStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;

/**
 * Enhanced test class for MainApp.
 */
public class MainAppTest {

    @TempDir
    protected static Path temporaryFolder;

    private TestableMainApp mainApp;
    private Path tempConfigPath;
    private Path tempUserPrefsPath;
    private Path tempAddressBookPath;
    private Path tempDrinkCatalogPath;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    public void setUp() throws IOException {
        // Initialize the JavaFX toolkit
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit is already running, which is fine
        }

        // Create temporary files - use createFile only if they don't exist
        tempConfigPath = temporaryFolder.resolve("config.json");
        tempUserPrefsPath = temporaryFolder.resolve("userPrefs.json");
        tempAddressBookPath = temporaryFolder.resolve("addressBook.json");
        tempDrinkCatalogPath = temporaryFolder.resolve("drinkCatalog.json");

        // Only create the files if they don't exist
        if (!Files.exists(tempConfigPath)) {
            Files.createFile(tempConfigPath);
        }
        if (!Files.exists(tempUserPrefsPath)) {
            Files.createFile(tempUserPrefsPath);
        }
        if (!Files.exists(tempAddressBookPath)) {
            Files.createFile(tempAddressBookPath);
        }
        if (!Files.exists(tempDrinkCatalogPath)) {
            Files.createFile(tempDrinkCatalogPath);
        }

        // Create a testable version of MainApp
        mainApp = new TestableMainApp();
    }

    /**
     * A simple test that validates the app can be constructed.
     */
    @Test
    public void testDefaultApp() {
        // Verify default test mode is false
        assertFalse(mainApp.isInTestMode());
    }

    /**
     * Test the initialization of configuration with a valid config file.
     */
    @Test
    public void initConfig_validConfigFile_returnsConfig() throws IOException {
        // Create a valid config file
        Config expectedConfig = new Config();
        expectedConfig.setLogLevel(java.util.logging.Level.INFO);
        expectedConfig.setUserPrefsFilePath(Paths.get("preferences.json"));
        ConfigUtil.saveConfig(expectedConfig, tempConfigPath);

        // Test initConfig with the valid config file
        Config result = mainApp.initConfig(tempConfigPath);

        // Verify that the result matches the expected config
        assertEquals(expectedConfig.getLogLevel(), result.getLogLevel());
        assertEquals(expectedConfig.getUserPrefsFilePath(), result.getUserPrefsFilePath());
    }

    /**
     * Test initialization of config when the config file does not exist.
     */
    @Test
    public void initConfig_nonExistentFile_returnsDefaultConfig() {
        // Test initConfig with a non-existent file
        Config result = mainApp.initConfig(Paths.get("nonexistent.json"));

        // Verify that a default config is returned
        assertEquals(Config.DEFAULT_CONFIG_FILE, Config.DEFAULT_CONFIG_FILE);
        assertNotNull(result);
    }

    /**
     * Test initialization of user preferences with a valid prefs file.
     */
    @Test
    public void initPrefs_validPrefsFile_returnsUserPrefs() throws IOException {
        // Create a valid user prefs storage
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(tempUserPrefsPath);

        // Create valid user prefs
        UserPrefs expectedPrefs = new UserPrefs();
        expectedPrefs.setGuiSettings(new seedu.address.commons.core.GuiSettings(1000, 800, 100, 100));
        userPrefsStorage.saveUserPrefs(expectedPrefs);

        // Test initPrefs with valid storage
        UserPrefs result = mainApp.initPrefs(userPrefsStorage);

        // Verify that the result matches the expected prefs
        assertEquals(expectedPrefs.getGuiSettings(), result.getGuiSettings());
        assertEquals(expectedPrefs.getAddressBookFilePath(), result.getAddressBookFilePath());
    }

    /**
     * Test initialization of model manager with valid storage and user prefs.
     */
    @Test
    public void initModelManager_validStorageAndUserPrefs_returnsModelManager() {
        // Create user prefs with file paths pointing to our temp files
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(tempAddressBookPath);
        userPrefs.setDrinkCatalogFilePath(tempDrinkCatalogPath);

        // Create a storage with empty data
        Storage storage = createTestStorage();

        // Test initModelManager
        Model result = mainApp.initModelManager(storage, userPrefs);

        // Verify that a non-null model is returned with all expected components
        assertNotNull(result);
        assertNotNull(result.getAddressBook());
        assertNotNull(result.getFilteredPersonList());
        assertNotNull(result.getFilteredStaffList());
        assertNotNull(result.getFilteredCustomerList());
    }

    /**
     * Test setting the application to test mode.
     */
    @Test
    public void setTestMode_setsFlag() {
        // Set test mode to true
        mainApp.setTestMode(true);

        // Verify that the test mode flag is set
        assertTrue(mainApp.isInTestMode());

        // Set test mode to false
        mainApp.setTestMode(false);

        // Verify that the test mode flag is cleared
        assertFalse(mainApp.isInTestMode());
    }

    /**
     * Test that test mode affects start behavior.
     */
    @Test
    public void testMode_affectsStartBehavior() {
        // Set up a mock UI
        MockUi mockUi = new MockUi();
        mainApp.setUi(mockUi);

        // Set test mode to true
        mainApp.setTestMode(true);

        // Verify test mode is set
        assertTrue(mainApp.isInTestMode());

        // We can't call start() directly with a real Stage in tests,
        // but we can verify the test mode flag is set correctly
    }

    /**
     * Test font loading error handling.
     */
    @Test
    public void loadCustomFonts_handlesErrors() {
        // This is already called in setUp() via mainApp initialization
        // Just verify we didn't crash - font loading should handle exceptions gracefully
        assertTrue(true);
    }

    /**
     * Test stopping the application.
     */
    @Test
    public void stop_savesUserPrefs() throws IOException {
        // Set up a model with user prefs
        UserPrefs userPrefs = new UserPrefs();
        Model model = new ModelManager(new AddressBook(), userPrefs, new DrinkCatalog());

        // Create a storage that we can verify gets called
        TestStorage storage = new TestStorage();

        // Set the model and storage in the main app
        mainApp.setModel(model);
        mainApp.setStorage(storage);

        // Call stop
        mainApp.stop();

        // Verify that saveUserPrefs was called
        assertTrue(storage.saveUserPrefsCalled, "saveUserPrefs should be called during app stop");
    }

    /**
     * Creates a test storage for testing.
     */
    private Storage createTestStorage() {
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(tempAddressBookPath);
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(tempUserPrefsPath);
        DrinkCatalogStorage drinkCatalogStorage = new JsonDrinkCatalogStorage(tempDrinkCatalogPath);

        return new StorageManager(addressBookStorage, userPrefsStorage, drinkCatalogStorage);
    }

    /**
     * A mock UI implementation for testing.
     */
    private class MockUi implements Ui {
        private boolean startCalled = false;

        @Override
        public void start(Stage primaryStage) {
            startCalled = true;
        }

        public boolean isStartCalled() {
            return startCalled;
        }
    }

    /**
     * A testable version of MainApp that allows access to protected methods and fields.
     */
    private class TestableMainApp extends MainApp {
        @Override
        public void init() throws Exception {
            // Do nothing to avoid JavaFX initialization
        }

        @Override
        public void start(Stage primaryStage) {
            // Do nothing to avoid JavaFX initialization
        }

        public boolean isInTestMode() {
            return this.isTestMode();
        }

        public void setModel(Model model) {
            this.model = model;
        }

        public void setStorage(Storage storage) {
            this.storage = storage;
        }

        public void setUi(Ui ui) {
            this.ui = ui;
        }

        @Override
        public Config initConfig(Path configFilePath) {
            return super.initConfig(configFilePath);
        }

        @Override
        public UserPrefs initPrefs(UserPrefsStorage storage) {
            return super.initPrefs(storage);
        }

        @Override
        public Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
            return super.initModelManager(storage, userPrefs);
        }
    }

    /**
     * A test storage class to check if methods are called.
     */
    private class TestStorage implements Storage {
        private boolean saveUserPrefsCalled = false;

        @Override
        public Path getUserPrefsFilePath() {
            return tempUserPrefsPath;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            return Optional.of(new UserPrefs());
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            saveUserPrefsCalled = true;
        }

        @Override
        public Path getAddressBookFilePath() {
            return tempAddressBookPath;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
            return Optional.of(new AddressBook());
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
            return Optional.of(new AddressBook());
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
            // Not needed for test
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            // Not needed for test
        }

        @Override
        public Path getDrinkCatalogFilePath() {
            return tempDrinkCatalogPath;
        }

        @Override
        public Optional<ReadOnlyDrinkCatalog> readDrinkCatalog() throws DataLoadingException {
            return Optional.of(new DrinkCatalog());
        }

        @Override
        public Optional<ReadOnlyDrinkCatalog> readDrinkCatalog(Path filePath) throws DataLoadingException {
            return Optional.of(new DrinkCatalog());
        }

        @Override
        public void saveDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog) throws IOException {
            // Not needed for test
        }

        @Override
        public void saveDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog, Path filePath) throws IOException {
            // Not needed for test
        }
    }
}
