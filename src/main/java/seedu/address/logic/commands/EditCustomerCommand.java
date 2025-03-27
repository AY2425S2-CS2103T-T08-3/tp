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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.tag.Tag;

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

        if (!customerToEdit.isSamePerson(editedCustomer) && model.hasPerson(editedCustomer)) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.setPerson(customerToEdit, editedCustomer);
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

        Name updatedName = editCustomerDescriptor.getName().orElse(customerToEdit.getName());
        Phone updatedPhone = editCustomerDescriptor.getPhone().orElse(customerToEdit.getPhone());
        Email updatedEmail = editCustomerDescriptor.getEmail().orElse(customerToEdit.getEmail());
        Address updatedAddress = editCustomerDescriptor.getAddress().orElse(customerToEdit.getAddress());
        Remark updatedRemark = customerToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = editCustomerDescriptor.getTags().orElse(customerToEdit.getTags());
        CustomerId updatedCustomerId = editCustomerDescriptor.getCustomerId().orElse(customerToEdit.getCustomerId());
        RewardPoints updatedRewardPoints = editCustomerDescriptor.getRewardPoints().orElse(
                customerToEdit.getRewardPoints());
        VisitCount updatedVisitCount = editCustomerDescriptor.getVisitCount().orElse(customerToEdit.getVisitCount());
        FavouriteItem updatedFavouriteItem = editCustomerDescriptor.getFavouriteItem().orElse(
                customerToEdit.getFavouriteItem());
        TotalSpent updatedTotalSpent = editCustomerDescriptor.getTotalSpent().orElse(customerToEdit.getTotalSpent());

        return new Customer(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRemark, updatedTags,
                updatedCustomerId, updatedRewardPoints, updatedVisitCount, updatedFavouriteItem, updatedTotalSpent);
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

    /**
     * Stores the details to edit the customer with. Each non-empty field value
     * will replace the corresponding field value of the customer.
     */
    public static class EditCustomerDescriptor extends EditPersonDescriptor {

        // Customer-specific fields
        private CustomerId customerId;
        private RewardPoints rewardPoints;
        private VisitCount visitCount;
        private FavouriteItem favouriteItem;
        private TotalSpent totalSpent;

        public EditCustomerDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used
         * internally.
         */
        public EditCustomerDescriptor(EditCustomerDescriptor toCopy) {
            super(toCopy);
            setPersonId(toCopy.customerId);
            setRewardPoints(toCopy.rewardPoints);
            setVisitCount(toCopy.visitCount);
            setFavouriteItem(toCopy.favouriteItem);
            setTotalSpent(toCopy.totalSpent);
        }

        @Override
        public boolean isAnyFieldEdited() {
            return super.isAnyFieldEdited()
                    || CollectionUtil.isAnyNonNull(customerId, rewardPoints, visitCount, favouriteItem, totalSpent);
        }

        public Optional<CustomerId> getCustomerId() {
            return Optional.ofNullable(customerId);
        }

        public void setPersonId(CustomerId customerId) {
            this.customerId = customerId;
        }

        public Optional<RewardPoints> getRewardPoints() {
            return Optional.ofNullable(rewardPoints);
        }

        public void setRewardPoints(RewardPoints rewardPoints) {
            this.rewardPoints = rewardPoints;
        }

        public Optional<VisitCount> getVisitCount() {
            return Optional.ofNullable(visitCount);
        }

        public void setVisitCount(VisitCount visitCount) {
            this.visitCount = visitCount;
        }

        public Optional<FavouriteItem> getFavouriteItem() {
            return Optional.ofNullable(favouriteItem);
        }

        public void setFavouriteItem(FavouriteItem favouriteItem) {
            this.favouriteItem = favouriteItem;
        }

        public Optional<TotalSpent> getTotalSpent() {
            return Optional.ofNullable(totalSpent);
        }

        public void setTotalSpent(TotalSpent totalSpent) {
            this.totalSpent = totalSpent;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCustomerDescriptor)) {
                return false;
            }

            if (!super.equals(other)) {
                return false;
            }

            EditCustomerDescriptor otherEditCustomerDescriptor = (EditCustomerDescriptor) other;
            return Objects.equals(customerId, otherEditCustomerDescriptor.customerId)
                    && Objects.equals(rewardPoints, otherEditCustomerDescriptor.rewardPoints)
                    && Objects.equals(visitCount, otherEditCustomerDescriptor.visitCount)
                    && Objects.equals(favouriteItem, otherEditCustomerDescriptor.favouriteItem)
                    && Objects.equals(totalSpent, otherEditCustomerDescriptor.totalSpent);
        }

        /**
         * Builds a string representation of the object using a {@link ToStringBuilder}.
         * This method extends the parent class's string representation by adding
         * additional fields specific to the customer entity, such as customer ID, rewardPoints,
         * visit count, favourite item, and total spent.
         *
         * @return A {@link ToStringBuilder} containing the string representation of the object.
         */
        @Override
        public ToStringBuilder toStringBuilder() {
            ToStringBuilder parentBuilder = super.toStringBuilder();
            parentBuilder.add("customerId", customerId)
                    .add("rewardPoints", rewardPoints)
                    .add("visitCount", visitCount)
                    .add("favouriteItem", favouriteItem)
                    .add("totalSpent", totalSpent);
            return parentBuilder;
        }
    }
}
