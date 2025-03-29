package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALEX;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStaffAtIndex;
import static seedu.address.testutil.TypicalDrinks.getTypicalDrinkCatalog;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalStaff.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.descriptors.EditStaffDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Staff;
import seedu.address.model.util.StaffBuilder;
import seedu.address.testutil.EditStaffDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditStaffCommand.
 */
public class EditStaffCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalDrinkCatalog());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Staff editedStaff = new StaffBuilder().build();
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(editedStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(
            EditStaffCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedStaff)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());
        expectedModel.setStaff(model.getFilteredStaffList().get(0), editedStaff);

        assertCommandSuccess(editStaffCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStaff = Index.fromOneBased(model.getFilteredStaffList().size());
        Staff lastStaff = model.getFilteredStaffList().get(indexLastStaff.getZeroBased());

        StaffBuilder staffInList = new StaffBuilder(lastStaff);
        Staff editedStaff = staffInList.withName(VALID_NAME_BEN).withPhone(VALID_PHONE_BEN)
                .withTags(VALID_TAG_HUSBAND).build();

        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_BEN)
                .withPhone(VALID_PHONE_BEN).withTags(VALID_TAG_HUSBAND).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(indexLastStaff, descriptor);

        String expectedMessage = String.format(
            EditStaffCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedStaff)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());
        expectedModel.setStaff(lastStaff, editedStaff);

        assertCommandSuccess(editStaffCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditStaffCommand editStaffCommand = new EditStaffCommand(
            INDEX_FIRST_PERSON, new EditStaffDescriptor()
        );
        Staff editedStaff = model.getFilteredStaffList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(
            EditStaffCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedStaff)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());

        assertCommandSuccess(editStaffCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStaffAtIndex(model, INDEX_FIRST_PERSON);

        Staff staffInFilteredList = model.getFilteredStaffList().get(INDEX_FIRST_PERSON.getZeroBased());
        Staff editedStaff = new StaffBuilder(staffInFilteredList).withName(VALID_NAME_BEN).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST_PERSON,
                new EditStaffDescriptorBuilder().withName(VALID_NAME_BEN).build());

        String expectedMessage = String.format(
            EditStaffCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedStaff)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());
        expectedModel.setStaff(model.getFilteredStaffList().get(0), editedStaff);

        assertCommandSuccess(editStaffCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStaffUnfilteredList_failure() {
        Staff firstStaff = model.getFilteredStaffList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder(firstStaff).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editStaffCommand, model, EditStaffCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateStaffFilteredList_failure() {
        showStaffAtIndex(model, INDEX_FIRST_PERSON);

        // edit staff in filtered list into a duplicate in staff book
        Staff staffInList = model.getAddressBook().getStaffList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditStaffCommand editStaffCommand = new EditStaffCommand(INDEX_FIRST_PERSON,
                new EditStaffDescriptorBuilder(staffInList).build());

        assertCommandFailure(editStaffCommand, model, EditStaffCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidStaffIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStaffList().size() + 1);
        EditStaffDescriptor descriptor = new EditStaffDescriptorBuilder().withName(VALID_NAME_BEN).build();
        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editStaffCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of staff book
     */
    @Test
    public void execute_invalidStaffIndexFilteredList_failure() {
        showStaffAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of staff book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStaffList().size());

        EditStaffCommand editStaffCommand = new EditStaffCommand(outOfBoundIndex,
                new EditStaffDescriptorBuilder().withName(VALID_NAME_BEN).build());

        assertCommandFailure(editStaffCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        final EditStaffCommand standardCommand = new EditStaffCommand(INDEX_FIRST_PERSON, DESC_ALEX);

        // same values -> returns true
        EditStaffDescriptor copyDescriptor = new EditStaffDescriptor(DESC_ALEX);
        EditStaffCommand commandWithSameValues = new EditStaffCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStaffCommand(INDEX_SECOND_PERSON, DESC_ALEX)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStaffCommand(INDEX_FIRST_PERSON, DESC_BEN)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditStaffDescriptor editStaffDescriptor = new EditStaffDescriptor();
        EditStaffCommand editStaffCommand = new EditStaffCommand(index, editStaffDescriptor);
        String expected = EditStaffCommand.class.getCanonicalName() + "{index=" + index + ", editStaffDescriptor="
                + editStaffDescriptor + "}";
        assertEquals(expected, editStaffCommand.toString());
    }
}
