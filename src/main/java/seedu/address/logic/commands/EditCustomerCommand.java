package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.descriptors.EditCustomerDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;
import seedu.address.model.util.CustomerBuilder;

/**
 * Edits the details of an existing customer in the address book.
 */
public class EditCustomerCommand extends Command {

    public static final String COMMAND_WORD = "customeredit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the customer identified "
            + "by the index number used in the displayed customer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_CUSTOMER_ID + "CUSTOMER_ID] "
            + "[" + PREFIX_REWARD_POINTS + "REWARD_POINTS] "
            + "[" + PREFIX_VISIT_COUNT + "VISIT_COUNT] "
            + "[" + PREFIX_FAVOURITE_ITEM + "FAVOURITE_ITEM] "
            + "[" + PREFIX_TOTAL_SPENT + "TOTAL_SPENT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "janedoe@example.com "
            + PREFIX_TAG + "VIP "
            + PREFIX_TOTAL_SPENT + "1000";

    public static final String MESSAGE_EDIT_CUSTOMER_SUCCESS = "Edited Customer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already exists in the address book.";

    private final Index index;
    private final EditCustomerDescriptor editCustomerDescriptor;

    /**
     * @param index of the customer in the filtered customer list to edit
     * @param editCustomerDescriptor details to edit the customer with
     */
    public EditCustomerCommand(Index index, EditCustomerDescriptor editCustomerDescriptor) {
        requireNonNull(index);
        requireNonNull(editCustomerDescriptor);

        this.index = index;
        this.editCustomerDescriptor = new EditCustomerDescriptor(editCustomerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Customer customerToEdit = lastShownList.get(index.getZeroBased());
        Customer editedCustomer = createEditedCustomer(customerToEdit, editCustomerDescriptor);

        if (!customerToEdit.isSamePerson(editedCustomer) && model.hasCustomer(editedCustomer)) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.setCustomer(customerToEdit, editedCustomer);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(String.format(MESSAGE_EDIT_CUSTOMER_SUCCESS, Messages.format(editedCustomer)));
    }

    /**
     * Creates and returns a {@code Customer} with the details of
     * {@code customerToEdit} edited with {@code editCustomerDescriptor}.
     */
    private static Customer createEditedCustomer(
            Customer customerToEdit,
            EditCustomerDescriptor editCustomerDescriptor) {
        assert customerToEdit != null;

        return new CustomerBuilder(customerToEdit)
                .withOptionalName(editCustomerDescriptor.getName())
                .withOptionalPhone(editCustomerDescriptor.getPhone())
                .withOptionalEmail(editCustomerDescriptor.getEmail())
                .withOptionalAddress(editCustomerDescriptor.getAddress())
                .withOptionalTags(editCustomerDescriptor.getTags())
                .withOptionalCustomerId(editCustomerDescriptor.getCustomerId())
                .withOptionalRewardPoints(editCustomerDescriptor.getRewardPoints())
                .withOptionalVisitCount(editCustomerDescriptor.getVisitCount())
                .withOptionalFavouriteItem(editCustomerDescriptor.getFavouriteItem())
                .withOptionalTotalSpent(editCustomerDescriptor.getTotalSpent())
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditCustomerCommand)) {
            return false;
        }

        EditCustomerCommand otherEditCustomerCommand = (EditCustomerCommand) other;
        return index.equals(otherEditCustomerCommand.index)
                && editCustomerDescriptor.equals(otherEditCustomerCommand.editCustomerDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCustomerDescriptor", editCustomerDescriptor)
                .toString();
    }
}
