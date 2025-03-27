package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteItem;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCustomerDescriptor objects.
 */
public class EditCustomerDescriptorBuilder {

    private EditCustomerDescriptor descriptor;

    public EditCustomerDescriptorBuilder() {
        descriptor = new EditCustomerDescriptor();
    }

    public EditCustomerDescriptorBuilder(EditCustomerDescriptor descriptor) {
        this.descriptor = new EditCustomerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCustomerDescriptorBuilder} with fields containing {@code customer}'s details
     */
    public EditCustomerDescriptorBuilder(Customer customer) {
        descriptor = new EditCustomerDescriptor();
        descriptor.setName(customer.getName());
        descriptor.setPhone(customer.getPhone());
        descriptor.setEmail(customer.getEmail());
        descriptor.setAddress(customer.getAddress());
        descriptor.setTags(customer.getTags());
        descriptor.setPersonId(customer.getCustomerId());
        descriptor.setRewardPoints(customer.getRewardPoints());
        descriptor.setVisitCount(customer.getVisitCount());
        descriptor.setFavouriteItem(customer.getFavouriteItem());
        descriptor.setTotalSpent(customer.getTotalSpent());
    }

    /**
     * Sets the {@code Name} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code CustomerId} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withCustomerId(String customerId) {
        descriptor.setPersonId(new CustomerId(customerId));
        return this;
    }

    /**
     * Sets the {@code RewardPoints} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withRewardPoints(String rewardPoints) {
        descriptor.setRewardPoints(new RewardPoints(rewardPoints));
        return this;
    }

    /**
     * Sets the {@code VisitCount} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withVisitCount(String visitCount) {
        descriptor.setVisitCount(new VisitCount(visitCount));
        return this;
    }

    /**
     * Sets the {@code FavouriteItem} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withFavouriteItem(String favouriteItem) {
        descriptor.setFavouriteItem(new FavouriteItem(favouriteItem));
        return this;
    }

    /**
     * Sets the {@code TotalSpent} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withTotalSpent(String totalSpent) {
        descriptor.setTotalSpent(new TotalSpent(totalSpent));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCustomerDescriptor}
     * that we are building.
     */
    public EditCustomerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCustomerDescriptor build() {
        return descriptor;
    }
}
