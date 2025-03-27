package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCustomerAtIndex;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDrinks.getTypicalDrinkCatalog;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Customer;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.EditCustomerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCustomerCommand.
 */
public class EditCustomerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalDrinkCatalog());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Customer editedCustomer = new CustomerBuilder().build();
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(editedCustomer).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(
            EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, Messages.format(editedCustomer)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());
        expectedModel.setPerson(model.getFilteredCustomerList().get(0), editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCustomer = Index.fromOneBased(model.getFilteredCustomerList().size());
        Customer lastCustomer = model.getFilteredCustomerList().get(indexLastCustomer.getZeroBased());

        CustomerBuilder customerInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = customerInList.withName(VALID_NAME_OLIVIA).withPhone(VALID_PHONE_OLIVIA)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_OLIVIA)
                .withPhone(VALID_PHONE_OLIVIA).withTags(VALID_TAG_HUSBAND).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(indexLastCustomer, descriptor);

        String expectedMessage = String.format(
            EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, Messages.format(editedCustomer)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());
        expectedModel.setPerson(lastCustomer, editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(
            INDEX_FIRST_PERSON, new EditCustomerDescriptor()
        );
        Customer editedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(
            EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, Messages.format(editedCustomer)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST_PERSON);

        Customer customerInFilteredList = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());
        Customer editedCustomer = new CustomerBuilder(customerInFilteredList).withName(VALID_NAME_OLIVIA).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_PERSON,
                new EditCustomerDescriptorBuilder().withName(VALID_NAME_OLIVIA).build());

        String expectedMessage = String.format(
            EditCustomerCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS, Messages.format(editedCustomer)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            new UserPrefs(), getTypicalDrinkCatalog());
        expectedModel.setPerson(model.getFilteredCustomerList().get(0), editedCustomer);

        assertCommandSuccess(editCustomerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCustomerUnfilteredList_failure() {
        Customer firstCustomer = model.getFilteredCustomerList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(firstCustomer).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCustomerCommand, model, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_duplicateCustomerFilteredList_failure() {
        showCustomerAtIndex(model, INDEX_FIRST_PERSON);

        // edit Customer in filtered list into a duplicate in Customer book
        Customer customerInList = model.getAddressBook().getCustomerList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(INDEX_FIRST_PERSON,
                new EditCustomerDescriptorBuilder(customerInList).build());

        assertCommandFailure(editCustomerCommand, model, EditCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_invalidCustomerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_OLIVIA).build();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of Customer book
     */
    @Test
    public void execute_invalidCustomerIndexFilteredList_failure() {
        showCustomerAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of Customer book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCustomerList().size());

        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(outOfBoundIndex,
                new EditCustomerDescriptorBuilder().withName(VALID_NAME_OLIVIA).build());

        assertCommandFailure(editCustomerCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        final EditCustomerCommand standardCommand = new EditCustomerCommand(INDEX_FIRST_PERSON, DESC_JAMES);

        // same values -> returns true
        EditCustomerDescriptor copyDescriptor = new EditCustomerDescriptor(DESC_JAMES);
        EditCustomerCommand commandWithSameValues = new EditCustomerCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_SECOND_PERSON, DESC_JAMES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCustomerCommand(INDEX_FIRST_PERSON, DESC_OLIVIA)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCustomerDescriptor editCustomerDescriptor = new EditCustomerDescriptor();
        EditCustomerCommand editCustomerCommand = new EditCustomerCommand(index, editCustomerDescriptor);
        String expected = EditCustomerCommand.class.getCanonicalName() + "{index=" + index + ", editCustomerDescriptor="
                + editCustomerDescriptor + "}";
        assertEquals(expected, editCustomerCommand.toString());
    }
}
