package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStaff.ALEX;

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
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.testutil.StaffBuilder;

public class AddStaffCommandTest {

    @Test
    public void constructor_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStaffCommand(null));
    }

    @Test
    public void execute_staffAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStaffAdded modelStub = new ModelStubAcceptingStaffAdded();
        Staff validStaff = new StaffBuilder().build();

        CommandResult commandResult = new AddStaffCommand(validStaff).execute(modelStub);

        assertEquals(String.format(AddStaffCommand.MESSAGE_SUCCESS, Messages.format(validStaff)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStaff), modelStub.staffAdded);
    }

    @Test
    public void execute_duplicateStaff_throwsCommandException() {
        Staff validStaff = new StaffBuilder().build();
        AddStaffCommand addStaffCommand = new AddStaffCommand(validStaff);
        ModelStub modelStub = new ModelStubWithStaff(validStaff);

        assertThrows(CommandException.class,
                AddStaffCommand.MESSAGE_DUPLICATE_STAFF, () -> addStaffCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Staff alex = new StaffBuilder().withName("Alex").build();
        Staff bob = new StaffBuilder().withName("Bob").build();
        AddStaffCommand addAlexCommand = new AddStaffCommand(alex);
        AddStaffCommand addBobCommand = new AddStaffCommand(bob);

        // same object -> returns true
        assertTrue(addAlexCommand.equals(addAlexCommand));

        // same values -> returns true
        AddStaffCommand addAlexCommandCopy = new AddStaffCommand(alex);
        assertTrue(addAlexCommand.equals(addAlexCommandCopy));

        // different types -> returns false
        assertFalse(addAlexCommand.equals(1));

        // null -> returns false
        assertFalse(addAlexCommand.equals(null));

        // different staff -> returns false
        assertFalse(addAlexCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddStaffCommand addStaffCommand = new AddStaffCommand(ALEX);
        String expected = AddStaffCommand.class.getCanonicalName() + "{toAdd=" + ALEX + "}";
        assertEquals(expected, addStaffCommand.toString());
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
        public void addStaff(Staff staffMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStaffList(Predicate<Staff> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStaff(Staff staffMember) {
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
    }

    private class ModelStubWithStaff extends ModelStub {
        private final Staff staff;

        ModelStubWithStaff(Staff staff) {
            requireNonNull(staff);
            this.staff = staff;
        }

        @Override
        public boolean hasStaff(Staff staffMember) {
            requireNonNull(staffMember);
            return this.staff.isSameStaff(staffMember);
        }
    }

    private class ModelStubAcceptingStaffAdded extends ModelStub {
        final ArrayList<Staff> staffAdded = new ArrayList<>();

        @Override
        public boolean hasStaff(Staff staffMember) {
            requireNonNull(staffMember);
            return staffAdded.stream().anyMatch(staffMember::isSameStaff);
        }

        @Override
        public void addStaff(Staff staffMember) {
            requireNonNull(staffMember);
            staffAdded.add(staffMember);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
