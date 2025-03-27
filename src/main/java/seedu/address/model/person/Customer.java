package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Customer in the café management system.
 * Inherits from Person and adds customer-specific attributes.
 */
public class Customer extends Person {

    // Customer-specific fields
    private final CustomerId customerId; // Customer id
    private final RewardPoints rewardPoints; // Current reward points balance
    private final VisitCount visitCount; // Number of times customer has visited
    private final FavouriteItem favouriteItem; // Customer's most ordered item
    private final TotalSpent totalSpent; // Total amount spent by customer

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags,
                    CustomerId customerId, RewardPoints rewardPoints, VisitCount visitCount,
                    FavouriteItem favouriteItem, TotalSpent totalSpent) {
        super(name, phone, email, address, remark, tags);
        requireAllNonNull(customerId, rewardPoints, visitCount, favouriteItem, totalSpent);

        this.customerId = customerId;
        this.rewardPoints = rewardPoints;
        this.visitCount = visitCount;
        this.favouriteItem = favouriteItem;
        this.totalSpent = totalSpent;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RewardPoints getRewardPoints() {
        return rewardPoints;
    }

    public VisitCount getVisitCount() {
        return visitCount;
    }

    public FavouriteItem getFavouriteItem() {
        return favouriteItem;
    }

    public TotalSpent getTotalSpent() {
        return totalSpent;
    }

    /**
     * Returns true if both customers have the same identity fields.
     * This defines a weaker notion of equality between two customers.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (!(otherPerson instanceof Customer)) {
            return false;
        }

        // Using phone number as unique identifier for customers
        return otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Returns true if both customers have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return super.equals(otherCustomer) // Calls the equality check from Person
                && rewardPoints.equals(otherCustomer.rewardPoints)
                && visitCount.equals(otherCustomer.visitCount)
                && favouriteItem.equals(otherCustomer.favouriteItem)
                && totalSpent.equals(otherCustomer.totalSpent)
                && customerId.equals(otherCustomer.customerId);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(customerId, rewardPoints, visitCount, favouriteItem, totalSpent);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("customerId", getCustomerId())
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("tags", getTags())
                .add("rewardPoints", rewardPoints)
                .add("visitCount", visitCount)
                .add("favouriteItem", favouriteItem)
                .add("totalSpent", totalSpent)
                .toString();
    }
}
