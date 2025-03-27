package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.JAMES;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.testutil.CustomerBuilder;

public class AddCustomerCommandTest {

    @Test
    public void constructor_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCustomerCommand(null));
    }

    @Test
    public void execute_customerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCustomerAdded modelStub = new ModelStubAcceptingCustomerAdded();
        Customer validCustomer = new CustomerBuilder().build();

        CommandResult commandResult = new AddCustomerCommand(validCustomer).execute(modelStub);

        assertEquals(String.format(AddCustomerCommand.MESSAGE_SUCCESS, Messages.format(validCustomer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCustomer), modelStub.customersAdded);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer validCustomer = new CustomerBuilder().build();
        AddCustomerCommand addCustomerCommand = new AddCustomerCommand(validCustomer);
        ModelStub modelStub = new ModelStubWithCustomer(validCustomer);

        assertThrows(CommandException.class,
                AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER, () -> addCustomerCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Customer james = new CustomerBuilder().withName("James Wilson").build();
        Customer olivia = new CustomerBuilder().withName("Olivia Chen").build();
        AddCustomerCommand addJamesCommand = new AddCustomerCommand(james);
        AddCustomerCommand addOliviaCommand = new AddCustomerCommand(olivia);

        // same object -> returns true
        assertTrue(addJamesCommand.equals(addJamesCommand));

        // same values -> returns true
        AddCustomerCommand addJamesCommandCopy = new AddCustomerCommand(james);
        assertTrue(addJamesCommand.equals(addJamesCommandCopy));

        // different types -> returns false
        assertFalse(addJamesCommand.equals(1));

        // null -> returns false
        assertFalse(addJamesCommand.equals(null));

        // different customer -> returns false
        assertFalse(addJamesCommand.equals(addOliviaCommand));
    }

    @Test
    public void toStringMethod() {
        AddCustomerCommand addCustomerCommand = new AddCustomerCommand(JAMES);
        String expected = AddCustomerCommand.class.getCanonicalName() + "{toAdd=" + JAMES + "}";
        assertEquals(expected, addCustomerCommand.toString());
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStaffList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCustomerList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDrink(Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDrink(Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDrink(Drink target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDrink(Drink target, Drink editedDrink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Drink> getFilteredDrinkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDrinkList(Predicate<Drink> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDrinkCatalog getDrinkCatalog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDrinkCatalogFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDrinkCatalogFilePath(Path drinkCatalogFilePath) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithCustomer extends ModelStub {
        private final Customer customer;

        ModelStubWithCustomer(Customer customer) {
            requireNonNull(customer);
            this.customer = customer;
        }
    }

    private class ModelStubAcceptingCustomerAdded extends ModelStub {
        final ArrayList<Customer> customersAdded = new ArrayList<>();

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
