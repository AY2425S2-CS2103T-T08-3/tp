package seedu.address.ui;

import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Staff;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    // Initialize the logger statically to ensure it's available before FXML loading
    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);

    private Stage primaryStage;
    private Logic logic;

    // Flag to track initialization state
    private boolean componentsInitialized = false;

    // Independent Ui parts residing in this Ui container
    private StaffListPanel staffListPanel;
    private CustomerListPanel customerListPanel;
    private DrinkListPanel drinkListPanel;
    private StaffDetailPanel staffDetailPanel;
    private CustomerDetailPanel customerDetailPanel;
    private DrinkDetailPanel drinkDetailPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private StatusBarFooter statusBarFooter;
    private HelpWindow helpWindow;

    // Store last selected items to restore when switching tabs
    private Staff lastSelectedStaff = null;
    private Customer lastSelectedCustomer = null;
    private Drink lastSelectedDrink = null;

    @FXML
    private TabPane tabPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane staffListPanelPlaceholder;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane drinksListPanelPlaceholder;

    @FXML
    private StackPane staffDetailsPlaceholder;

    @FXML
    private StackPane customerDetailsPlaceholder;

    @FXML
    private StackPane drinksDetailsPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox mainPane;

    @FXML
    private MenuBar menuBar;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setAccelerators();
        helpWindow = new HelpWindow();
    }

    /**
     * Sets up the stage after FXML initialization is complete.
     * Call this after construction to ensure primaryStage is properly set.
     */
    public void setUpStage() {
        if (primaryStage != null) {
            // Set window close handler
            primaryStage.setOnCloseRequest(event -> {
                logger.info("Window close requested, handling exit");
                handleExit();
            });
        } else {
            logger.warning("Primary stage is null in setUpStage()");
        }
    }

    /**
     * Initialize method called by JavaFX after FXML loading.
     * Sets up all event handlers programmatically.
     */
    @FXML
    private void initialize() {
        logger.info("Initializing MainWindow components");

        try {
            // Set up menu handlers
            if (menuBar != null && !menuBar.getMenus().isEmpty()
                    && !menuBar.getMenus().get(0).getItems().isEmpty()) {
                menuBar.getMenus().get(0).getItems().get(0).setOnAction(event -> handleExit());
            } else {
                logger.warning("Menu bar structure not as expected during initialization");
            }

            if (helpMenuItem != null) {
                helpMenuItem.setOnAction(event -> handleHelp());
            } else {
                logger.warning("Help menu item is null during initialization");
            }

            // Initialize tab change handlers if tabPane is available
            if (tabPane != null) {
                setupTabChangeHandlers();
            } else {
                logger.warning("Tab pane is null during initialization");
            }
        } catch (Exception e) {
            logger.severe("Error during MainWindow initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param menuItem the MenuItem to set the accelerator for
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        if (menuItem == null) {
            logger.warning("Cannot set accelerator for null menu item");
            return;
        }

        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Sets up tab change handlers to preserve selections when switching tabs.
     */
    private void setupTabChangeHandlers() {
        // Handle tab changes
        tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int tabIndex = newVal.intValue();
            logger.info("Tab changed to index " + tabIndex);

            switch (tabIndex) {
            case 0: // Staff tab
                logger.info("Switched to Staff tab");
                // Restore previous staff selection if any
                if (lastSelectedStaff != null && staffListPanel != null) {
                    staffListPanel.selectStaff(lastSelectedStaff);
                }
                // Ensure command box has focus
                focusCommandBox();
                break;

            case 1: // Customer tab
                logger.info("Switched to Customer tab");
                // Restore previous customer selection if any
                if (lastSelectedCustomer != null && customerListPanel != null) {
                    customerListPanel.selectCustomer(lastSelectedCustomer);
                }
                // Ensure command box has focus
                focusCommandBox();
                break;

            case 2: // Drinks tab
                logger.info("Switched to Drinks tab");
                // Restore previous drink selection if any
                if (lastSelectedDrink != null && drinkListPanel != null) {
                    drinkListPanel.selectDrink(lastSelectedDrink);
                }
                // Ensure command box has focus
                focusCommandBox();
                break;

            default:
                logger.warning("Unknown tab index: " + tabIndex);
                break;
            }
        });
    }

    /**
     * Ensures the command box has focus to enable typing.
     */
    private void focusCommandBox() {
        if (commandBox != null) {
            Platform.runLater(() -> commandBox.focus());
        }
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        logger.info("Filling main window inner parts");

        // Prevent duplicate initialization
        if (componentsInitialized) {
            logger.info("Components already initialized, skipping");
            return;
        }

        try {
            // Staff components
            staffListPanel = new StaffListPanel(logic.getFilteredStaffList());
            staffListPanelPlaceholder.getChildren().add(staffListPanel.getRoot());

            staffDetailPanel = new StaffDetailPanel();
            staffDetailsPlaceholder.getChildren().add(staffDetailPanel.getRoot());

            // Customer components
            customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList());
            customerListPanelPlaceholder.getChildren().add(customerListPanel.getRoot());

            customerDetailPanel = new CustomerDetailPanel();
            customerDetailsPlaceholder.getChildren().add(customerDetailPanel.getRoot());

            // Drinks components - with safe fallback
            ObservableList<Drink> drinksList;
            try {
                drinksList = logic.getFilteredDrinkList();
                logger.info("Successfully loaded drinks list from logic");
            } catch (Exception e) {
                // Fall back to sample drinks if the method doesn't exist or has issues
                logger.info("Using sample drinks data: " + e.getMessage());
                drinksList = FXCollections.observableArrayList(
                        new Drink("Espresso", 3.50, "Coffee"),
                        new Drink("Latte", 4.50, "Coffee"),
                        new Drink("Cappuccino", 4.00, "Coffee"),
                        new Drink("Green Tea", 3.00, "Tea"),
                        new Drink("Orange Juice", 2.50, "Juice")
                );
            }

            drinkListPanel = new DrinkListPanel(drinksList);
            drinksListPanelPlaceholder.getChildren().add(drinkListPanel.getRoot());

            drinkDetailPanel = new DrinkDetailPanel();
            drinksDetailsPlaceholder.getChildren().add(drinkDetailPanel.getRoot());

            // Set up selection handlers
            if (staffListPanel != null) {
                staffListPanel.setPersonSelectionHandler(this::handleStaffSelection);
            }
            if (customerListPanel != null) {
                customerListPanel.setPersonSelectionHandler(this::handleCustomerSelection);
            }
            if (drinkListPanel != null) {
                drinkListPanel.setDrinkSelectionHandler(this::handleDrinkSelection);
            }

            // Create result display
            resultDisplay = new ResultDisplay();
            resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

            // Create status bar with safe path handling
            try {
                statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
                statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
            } catch (Exception e) {
                logger.warning("Error initializing status bar: " + e.getMessage());
                // Create a fallback status bar with a safe path
                statusBarFooter = new StatusBarFooter(Paths.get("data", "addressbook.json"));
                statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
            }

            // Create command box
            commandBox = new CommandBox(this::executeCommand);
            commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

            // Focus the command box to enable typing
            Platform.runLater(() -> {
                if (commandBox != null) {
                    commandBox.focus();
                }
            });

            // Mark initialization as complete
            componentsInitialized = true;

        } catch (Exception e) {
            logger.severe("Error initializing UI components: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles staff selection from the list.
     */
    private void handleStaffSelection(Staff staff) {
        logger.info("Staff selected: " + (staff != null ? staff.getName().fullName : "none"));
        lastSelectedStaff = staff;
        if (staffDetailPanel != null) {
            staffDetailPanel.updateStaffDetails(staff);
        }
    }

    /**
     * Handles customer selection from the list.
     */
    private void handleCustomerSelection(Customer customer) {
        logger.info("Customer selected: " + (customer != null && customer.getName() != null
                ? customer.getName().fullName : "none"));
        lastSelectedCustomer = customer;
        if (customerDetailPanel != null) {
            customerDetailPanel.updateCustomerDetails(customer);
        }
    }

    /**
     * Handles drink selection from the list.
     */
    private void handleDrinkSelection(Drink drink) {
        logger.info("Drink selected: " + (drink != null ? drink.getName() : "none"));
        lastSelectedDrink = drink;
        if (drinkDetailPanel != null) {
            drinkDetailPanel.updateDrinkDetails(drink);
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        if (primaryStage == null) {
            logger.warning("Cannot set window size - primary stage is null");
            return;
        }

        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Shows the main window.
     */
    void show() {
        primaryStage.show();

        // Focus command box after showing window
        Platform.runLater(() -> {
            if (commandBox != null) {
                commandBox.focus();
            }
        });
    }

    /**
     * Closes the application.
     */
    private void handleExit() {
        try {
            GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                    (int) primaryStage.getX(), (int) primaryStage.getY());
            logic.setGuiSettings(guiSettings);

            if (helpWindow != null) {
                helpWindow.hide();
            }

            primaryStage.hide();
        } catch (Exception e) {
            logger.warning("Error during application exit: " + e.getMessage());
            // Ensure the application exits even if there's an error
            primaryStage.hide();
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Selects the specified tab.
     * @param tabIndex The index of the tab to select (0-based)
     */
    public void selectTab(int tabIndex) {
        if (tabPane != null && tabIndex >= 0 && tabIndex < tabPane.getTabs().size()) {
            tabPane.getSelectionModel().select(tabIndex);
        } else {
            logger.warning("Could not select tab " + tabIndex + " - tab pane or index is invalid");
        }
    }
}
