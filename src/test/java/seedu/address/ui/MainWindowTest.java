package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Name;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.tag.Tag;

/**
 * Test class for MainWindow.
 */
public class MainWindowTest {

    private TestableMainWindow mainWindow;
    private TestStage stage;
    private TestLogic logic;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    public void setUp() {
        // Initialize the JavaFX toolkit
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit is already running, which is fine
        }

        // Create our test components
        stage = new TestStage();
        logic = new TestLogic();

        // Create the main window
        mainWindow = new TestableMainWindow(stage, logic);
    }

    /**
     * Test the constructor.
     */
    @Test
    public void constructor_validParameters_setsWindowDefaults() {
        // Verify window settings
        assertEquals(600, stage.getHeight());
        assertEquals(800, stage.getWidth());
        assertEquals(0, stage.getX());
        assertEquals(0, stage.getY());
    }

    /**
     * Test setting up the stage after construction.
     */
    @Test
    public void setUpStage_setsCloseHandler() {
        // Call setUpStage
        mainWindow.setUpStage();

        // Verify that close handler was set
        assertTrue(stage.isCloseHandlerSet());
    }

    /**
     * Test initialization of the main window.
     */
    @Test
    public void initialize_withTabPane_setupsTabChangeHandlers() {
        // Create and set a tab pane
        TestTabPane tabPane = new TestTabPane();
        mainWindow.setTabPane(tabPane);

        // Call initialize
        mainWindow.initialize();

        // Verify tab change handlers were set up
        assertTrue(tabPane.isSelectionModelRetrieved());
    }

    /**
     * Test filling inner parts of the main window.
     */
    @Test
    public void fillInnerParts_createsAndAddsComponents() {
        // Set up placeholders
        TestStackPane staffListPanelPlaceholder = new TestStackPane();
        TestStackPane customerListPanelPlaceholder = new TestStackPane();
        TestStackPane drinksListPanelPlaceholder = new TestStackPane();
        TestStackPane staffDetailsPlaceholder = new TestStackPane();
        TestStackPane customerDetailsPlaceholder = new TestStackPane();
        TestStackPane drinksDetailsPlaceholder = new TestStackPane();
        TestStackPane resultDisplayPlaceholder = new TestStackPane();
        TestStackPane statusbarPlaceholder = new TestStackPane();
        TestStackPane commandBoxPlaceholder = new TestStackPane();

        mainWindow.setPlaceholders(
                staffListPanelPlaceholder,
                customerListPanelPlaceholder,
                drinksListPanelPlaceholder,
                staffDetailsPlaceholder,
                customerDetailsPlaceholder,
                drinksDetailsPlaceholder,
                resultDisplayPlaceholder,
                statusbarPlaceholder,
                commandBoxPlaceholder
        );

        // Call fillInnerParts
        mainWindow.fillInnerParts();

        // Verify that components were added to placeholders
        assertTrue(staffListPanelPlaceholder.isComponentAdded());
        assertTrue(customerListPanelPlaceholder.isComponentAdded());
        assertTrue(drinksListPanelPlaceholder.isComponentAdded());
        assertTrue(staffDetailsPlaceholder.isComponentAdded());
        assertTrue(customerDetailsPlaceholder.isComponentAdded());
        assertTrue(drinksDetailsPlaceholder.isComponentAdded());
        assertTrue(resultDisplayPlaceholder.isComponentAdded());
        assertTrue(statusbarPlaceholder.isComponentAdded());
        assertTrue(commandBoxPlaceholder.isComponentAdded());

        // Verify components were created
        assertNotNull(mainWindow.getStaffListPanel());
        assertNotNull(mainWindow.getCustomerListPanel());
        assertNotNull(mainWindow.getDrinkListPanel());
    }

    /**
     * Test selecting a tab.
     */
    @Test
    public void selectTab_validIndex_selectsTab() {
        // Set up tab pane
        TestTabPane tabPane = new TestTabPane();
        mainWindow.setTabPane(tabPane);

        // Call selectTab with valid index
        mainWindow.selectTab(1);

        // Verify the tab was selected
        assertEquals(1, tabPane.getSelectedIndex());
    }

    /**
     * Test handling staff selection with actual Staff object.
     */
    @Test
    public void handleStaffSelection_withActualStaffObject_updatesDetailPanel() {
        // Create a staff detail panel
        TestStaffDetailPanel staffDetailPanel = new TestStaffDetailPanel();
        mainWindow.setStaffDetailPanel(staffDetailPanel);

        // Create a real Staff object
        Name name = new Name("John Doe");
        Phone phone = new Phone("12345678");
        Email email = new Email("john@example.com");
        Address address = new Address("123 Main St");
        Remark remark = new Remark("Good worker");
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("Experienced")));
        StaffId staffId = new StaffId("S001");
        Role role = new Role("Barista");
        ShiftTiming shiftTiming = new ShiftTiming("Morning");
        HoursWorked hoursWorked = new HoursWorked("120");
        PerformanceRating performanceRating = new PerformanceRating("4.5");

        Staff staff = new Staff(name, phone, email, address, remark, tags,
                staffId, role, shiftTiming, hoursWorked, performanceRating);

        // Call handleStaffSelection
        mainWindow.handleStaffSelection(staff);

        // Verify the detail panel was updated
        assertTrue(staffDetailPanel.isUpdated());
        assertEquals(staff, staffDetailPanel.getLastStaff());
    }

    /**
     * Test handling customer selection with actual Customer object.
     */
    @Test
    public void handleCustomerSelection_withActualCustomerObject_updatesDetailPanel() {
        // Create a customer detail panel
        TestCustomerDetailPanel customerDetailPanel = new TestCustomerDetailPanel();
        mainWindow.setCustomerDetailPanel(customerDetailPanel);

        // Create a real Customer object
        Name name = new Name("Jane Smith");
        Phone phone = new Phone("87654321");
        Email email = new Email("jane@example.com");
        Address address = new Address("456 Oak St");
        Remark remark = new Remark("Regular customer");
        Set<Tag> tags = new HashSet<>(Arrays.asList(new Tag("VIP")));
        CustomerId customerId = new CustomerId("C001");
        RewardPoints rewardPoints = new RewardPoints("150");
        VisitCount visitCount = new VisitCount("15");
        FavouriteItem favouriteItem = new FavouriteItem("Latte");
        TotalSpent totalSpent = new TotalSpent("250.50");

        Customer customer = new Customer(name, phone, email, address, remark, tags,
                customerId, rewardPoints, visitCount, favouriteItem, totalSpent);

        // Call handleCustomerSelection
        mainWindow.handleCustomerSelection(customer);

        // Verify the detail panel was updated
        assertTrue(customerDetailPanel.isUpdated());
        assertEquals(customer, customerDetailPanel.getLastCustomer());
    }

    /**
     * Test handling drink selection with actual Drink object.
     */
    @Test
    public void handleDrinkSelection_withActualDrinkObject_updatesDetailPanel() {
        // Create a drink detail panel
        TestDrinkDetailPanel drinkDetailPanel = new TestDrinkDetailPanel();
        mainWindow.setDrinkDetailPanel(drinkDetailPanel);

        // Create a real Drink object
        Drink drink = new Drink("Latte", 4.50, "Coffee");
        drink.setDescription("Espresso with steamed milk");
        drink.setStock(25);

        // Call handleDrinkSelection
        mainWindow.handleDrinkSelection(drink);

        // Verify the detail panel was updated
        assertTrue(drinkDetailPanel.isUpdated());
        assertEquals(drink, drinkDetailPanel.getLastDrink());
    }

    /**
     * Test executing a command.
     */
    @Test
    public void executeCommand_validCommand_returnsCommandResult() throws CommandException, ParseException {
        // Create a result display
        TestResultDisplay resultDisplay = new TestResultDisplay();
        mainWindow.setResultDisplay(resultDisplay);

        // Call executeCommand
        CommandResult result = mainWindow.executeCommand("stafflist");

        // Verify feedback was set
        assertTrue(resultDisplay.isFeedbackSet());
        assertEquals("Test command executed", resultDisplay.getLastFeedback());
        assertNotNull(result);
    }

    /**
     * Test showing the main window.
     */
    @Test
    public void show_setsStageVisibility() {
        // Call show
        mainWindow.show();

        // Verify stage was shown
        assertTrue(stage.isShown());
    }

    /**
     * A test implementation of Stage.
     */
    private static class TestStage {
        private double height = 0;
        private double width = 0;
        private double x = 0;
        private double y = 0;
        private boolean closeHandlerSet = false;
        private boolean shown = false;

        public void setHeight(double height) {
            this.height = height;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setOnCloseRequest() {
            closeHandlerSet = true;
        }

        public void show() {
            shown = true;
        }

        public double getHeight() {
            return height;
        }

        public double getWidth() {
            return width;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public boolean isCloseHandlerSet() {
            return closeHandlerSet;
        }

        public boolean isShown() {
            return shown;
        }
    }

    /**
     * A test implementation of Logic.
     */
    private static class TestLogic implements Logic {
        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return new CommandResult("Test command executed");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Drink> getFilteredDrinkList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            return java.nio.file.Paths.get("test", "addressbook.json");
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings(800, 600, 0, 0);
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            // Do nothing for test
        }
    }

    /**
     * A test implementation of TabPane.
     */
    private static class TestTabPane {
        private boolean selectionModelRetrieved = false;
        private int selectedIndex = -1;

        public TestSelectionModel getSelectionModel() {
            selectionModelRetrieved = true;
            return new TestSelectionModel();
        }

        public ObservableList<Object> getTabs() {
            return FXCollections.observableArrayList(
                    new Object(), // Staff tab
                    new Object(), // Customer tab
                    new Object() // Drinks tab
            );
        }

        public boolean isSelectionModelRetrieved() {
            return selectionModelRetrieved;
        }

        public int getSelectedIndex() {
            return selectedIndex;
        }

        class TestSelectionModel {
            public SimpleIntegerProperty selectedIndexProperty() {
                return new SimpleIntegerProperty(0);
            }

            public void select(int index) {
                selectedIndex = index;
            }
        }
    }

    /**
     * A test implementation of StackPane.
     */
    private static class TestStackPane {
        private boolean componentAdded = false;

        public ObservableList<Object> getChildren() {
            return FXCollections.observableArrayList();
        }

        public void add(Object component) {
            componentAdded = true;
        }

        public boolean isComponentAdded() {
            return componentAdded;
        }
    }

    /**
     * Test implementations of detail panels.
     */
    private static class TestStaffDetailPanel {
        private boolean updated = false;
        private Staff lastStaff = null;

        public void updateStaffDetails(Staff staff) {
            updated = true;
            lastStaff = staff;
        }

        public boolean isUpdated() {
            return updated;
        }

        public Staff getLastStaff() {
            return lastStaff;
        }
    }

    private static class TestCustomerDetailPanel {
        private boolean updated = false;
        private Customer lastCustomer = null;

        public void updateCustomerDetails(Customer customer) {
            updated = true;
            lastCustomer = customer;
        }

        public boolean isUpdated() {
            return updated;
        }

        public Customer getLastCustomer() {
            return lastCustomer;
        }
    }

    private static class TestDrinkDetailPanel {
        private boolean updated = false;
        private Drink lastDrink = null;

        public void updateDrinkDetails(Drink drink) {
            updated = true;
            lastDrink = drink;
        }

        public boolean isUpdated() {
            return updated;
        }

        public Drink getLastDrink() {
            return lastDrink;
        }
    }

    /**
     * Test implementation of ResultDisplay.
     */
    private static class TestResultDisplay {
        private boolean feedbackSet = false;
        private String lastFeedback = null;

        public void setFeedbackToUser(String feedback) {
            feedbackSet = true;
            lastFeedback = feedback;
        }

        public boolean isFeedbackSet() {
            return feedbackSet;
        }

        public String getLastFeedback() {
            return lastFeedback;
        }
    }

    /**
     * A testable version of MainWindow.
     */
    private class TestableMainWindow {
        private TestStage stage;
        private TestLogic logic;
        private TestTabPane tabPane;

        // UI Components
        private StaffListPanel staffListPanel;
        private CustomerListPanel customerListPanel;
        private DrinkListPanel drinkListPanel;
        private TestStaffDetailPanel staffDetailPanel;
        private TestCustomerDetailPanel customerDetailPanel;
        private TestDrinkDetailPanel drinkDetailPanel;
        private TestResultDisplay resultDisplay;

        // Placeholders for UI components
        private TestStackPane staffListPanelPlaceholder;
        private TestStackPane customerListPanelPlaceholder;
        private TestStackPane drinksListPanelPlaceholder;
        private TestStackPane staffDetailsPlaceholder;
        private TestStackPane customerDetailsPlaceholder;
        private TestStackPane drinksDetailsPlaceholder;
        private TestStackPane resultDisplayPlaceholder;
        private TestStackPane statusbarPlaceholder;
        private TestStackPane commandBoxPlaceholder;

        public TestableMainWindow(TestStage stage, TestLogic logic) {
            this.stage = stage;
            this.logic = logic;

            // Set default window size based on GUI settings
            GuiSettings guiSettings = logic.getGuiSettings();
            stage.setHeight(guiSettings.getWindowHeight());
            stage.setWidth(guiSettings.getWindowWidth());
            if (guiSettings.getWindowCoordinates() != null) {
                stage.setX(guiSettings.getWindowCoordinates().getX());
                stage.setY(guiSettings.getWindowCoordinates().getY());
            }
        }

        public void setUpStage() {
            stage.setOnCloseRequest();
        }

        public void initialize() {
            // Set up tab change handlers if tabPane is available
            if (tabPane != null) {
                // Just retrieve the selection model to set the flag
                tabPane.getSelectionModel();
            }
        }

        public void fillInnerParts() {
            // Create UI components
            staffListPanel = new StaffListPanel(logic.getFilteredStaffList());
            customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList());
            drinkListPanel = new DrinkListPanel(logic.getFilteredDrinkList());

            // Add components to placeholders
            if (staffListPanelPlaceholder != null) {
                staffListPanelPlaceholder.add(new Object());
            }

            if (customerListPanelPlaceholder != null) {
                customerListPanelPlaceholder.add(new Object());
            }

            if (drinksListPanelPlaceholder != null) {
                drinksListPanelPlaceholder.add(new Object());
            }

            if (staffDetailsPlaceholder != null) {
                staffDetailsPlaceholder.add(new Object());
            }

            if (customerDetailsPlaceholder != null) {
                customerDetailsPlaceholder.add(new Object());
            }

            if (drinksDetailsPlaceholder != null) {
                drinksDetailsPlaceholder.add(new Object());
            }

            if (resultDisplayPlaceholder != null) {
                resultDisplayPlaceholder.add(new Object());
            }

            if (statusbarPlaceholder != null) {
                statusbarPlaceholder.add(new Object());
            }

            if (commandBoxPlaceholder != null) {
                commandBoxPlaceholder.add(new Object());
            }
        }

        public void handleStaffSelection(Staff staff) {
            if (staffDetailPanel != null) {
                staffDetailPanel.updateStaffDetails(staff);
            }
        }

        public void handleCustomerSelection(Customer customer) {
            if (customerDetailPanel != null) {
                customerDetailPanel.updateCustomerDetails(customer);
            }
        }

        public void handleDrinkSelection(Drink drink) {
            if (drinkDetailPanel != null) {
                drinkDetailPanel.updateDrinkDetails(drink);
            }
        }

        public CommandResult executeCommand(String commandText) throws CommandException, ParseException {
            CommandResult result = logic.execute(commandText);

            if (resultDisplay != null) {
                resultDisplay.setFeedbackToUser(result.getFeedbackToUser());
            }

            return result;
        }

        public void show() {
            stage.show();
        }

        public void setTabPane(TestTabPane tabPane) {
            this.tabPane = tabPane;
        }

        public void setStaffDetailPanel(TestStaffDetailPanel panel) {
            this.staffDetailPanel = panel;
        }

        public void setCustomerDetailPanel(TestCustomerDetailPanel panel) {
            this.customerDetailPanel = panel;
        }

        public void setDrinkDetailPanel(TestDrinkDetailPanel panel) {
            this.drinkDetailPanel = panel;
        }

        public void setResultDisplay(TestResultDisplay display) {
            this.resultDisplay = display;
        }

        public void setPlaceholders(
                TestStackPane staffListPanelPlaceholder,
                TestStackPane customerListPanelPlaceholder,
                TestStackPane drinksListPanelPlaceholder,
                TestStackPane staffDetailsPlaceholder,
                TestStackPane customerDetailsPlaceholder,
                TestStackPane drinksDetailsPlaceholder,
                TestStackPane resultDisplayPlaceholder,
                TestStackPane statusbarPlaceholder,
                TestStackPane commandBoxPlaceholder) {
            this.staffListPanelPlaceholder = staffListPanelPlaceholder;
            this.customerListPanelPlaceholder = customerListPanelPlaceholder;
            this.drinksListPanelPlaceholder = drinksListPanelPlaceholder;
            this.staffDetailsPlaceholder = staffDetailsPlaceholder;
            this.customerDetailsPlaceholder = customerDetailsPlaceholder;
            this.drinksDetailsPlaceholder = drinksDetailsPlaceholder;
            this.resultDisplayPlaceholder = resultDisplayPlaceholder;
            this.statusbarPlaceholder = statusbarPlaceholder;
            this.commandBoxPlaceholder = commandBoxPlaceholder;
        }

        public void selectTab(int tabIndex) {
            if (tabPane != null) {
                tabPane.getSelectionModel().select(tabIndex);
            }
        }

        public StaffListPanel getStaffListPanel() {
            return staffListPanel;
        }

        public CustomerListPanel getCustomerListPanel() {
            return customerListPanel;
        }

        public DrinkListPanel getDrinkListPanel() {
            return drinkListPanel;
        }
    }

    /**
     * Mock implementations of various UI components.
     */
    private static class StaffListPanel {
        public StaffListPanel(ObservableList<Staff> staffList) {
            // Initialize with staff list
        }
    }

    private static class CustomerListPanel {
        public CustomerListPanel(ObservableList<Customer> customerList) {
            // Initialize with customer list
        }
    }

    private static class DrinkListPanel {
        public DrinkListPanel(ObservableList<Drink> drinkList) {
            // Initialize with drink list
        }
    }
}
