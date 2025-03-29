package seedu.address.model.util;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "Regular customer";
    public static final String DEFAULT_CUSTOMER_ID = "C1001";
    public static final String DEFAULT_REWARD_POINTS = "100";
    public static final String DEFAULT_VISIT_COUNT = "5";
    public static final String DEFAULT_FAVOURITE_ITEM = "Latte";
    public static final String DEFAULT_TOTAL_SPENT = "75.50";
    public static final String DEFAULT_RATING = "5";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;
    private CustomerId customerId;
    private RewardPoints rewardPoints;
    private VisitCount visitCount;
    private FavouriteItem favouriteItem;
    private TotalSpent totalSpent;

    /**
     * Creates a {@code CustomerBuilder} with the default details.
     */
    public CustomerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        customerId = new CustomerId(DEFAULT_CUSTOMER_ID);
        rewardPoints = new RewardPoints(DEFAULT_REWARD_POINTS);
        visitCount = new VisitCount(DEFAULT_VISIT_COUNT);
        favouriteItem = new FavouriteItem(DEFAULT_FAVOURITE_ITEM);
        totalSpent = new TotalSpent(DEFAULT_TOTAL_SPENT);
    }

    /**
     * Initializes the CustomerBuilder with the data of {@code customerToCopy}.
     */
    public CustomerBuilder(Customer customerToCopy) {
        name = customerToCopy.getName();
        phone = customerToCopy.getPhone();
        email = customerToCopy.getEmail();
        address = customerToCopy.getAddress();
        remark = customerToCopy.getRemark();
        tags = new HashSet<>(customerToCopy.getTags());
        customerId = customerToCopy.getCustomerId();
        rewardPoints = customerToCopy.getRewardPoints();
        visitCount = customerToCopy.getVisitCount();
        favouriteItem = customerToCopy.getFavouriteItem();
        totalSpent = customerToCopy.getTotalSpent();
    }

    /**
     * Sets the {@code Name} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withName(Name name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalName(Optional<Name> nameOptional) {
        nameOptional.ifPresent(n -> this.name = n);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalPhone(Optional<Phone> phoneOptional) {
        phoneOptional.ifPresent(p -> this.phone = p);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withEmail(Email email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalEmail(Optional<Email> emailOptional) {
        emailOptional.ifPresent(e -> this.email = e);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalAddress(Optional<Address> addressOptional) {
        addressOptional.ifPresent(a -> this.address = a);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withRemark(Remark remark) {
        this.remark = remark;
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalRemark(Optional<Remark> remarkOptional) {
        remarkOptional.ifPresent(r -> this.remark = r);
        return this;
    }

    /**
     * Sets the {@code Tags} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Tags} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withTags(Set<Tag> tags) {
        this.tags = new HashSet<>(tags);
        return this;
    }

    /**
     * Sets the {@code Set<Tag>} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalTags(Optional<Set<Tag>> tagsOptional) {
        tagsOptional.ifPresent(t -> this.tags = t);
        return this;
    }

    /**
     * Sets the {@code CustomerId} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withCustomerId(String customerId) {
        this.customerId = new CustomerId(customerId);
        return this;
    }

    /**
     * Sets the {@code CustomerId} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withCustomerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Sets the {@code CustomerId} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalCustomerId(Optional<CustomerId> customerIdOptional) {
        customerIdOptional.ifPresent(id -> this.customerId = id);
        return this;
    }

    /**
     * Sets the {@code RewardPoints} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withRewardPoints(String rewardPoints) {
        this.rewardPoints = new RewardPoints(rewardPoints);
        return this;
    }

    /**
     * Sets the {@code RewardPoints} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withRewardPoints(RewardPoints rewardPoints) {
        this.rewardPoints = rewardPoints;
        return this;
    }

    /**
     * Sets the {@code RewardPoints} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalRewardPoints(Optional<RewardPoints> rewardPointsOptional) {
        rewardPointsOptional.ifPresent(rp -> this.rewardPoints = rp);
        return this;
    }

    /**
     * Sets the {@code VisitCount} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withVisitCount(String visitCount) {
        this.visitCount = new VisitCount(visitCount);
        return this;
    }

    /**
     * Sets the {@code VisitCount} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withVisitCount(VisitCount visitCount) {
        this.visitCount = visitCount;
        return this;
    }

    /**
     * Sets the {@code VisitCount} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalVisitCount(Optional<VisitCount> visitCountOptional) {
        visitCountOptional.ifPresent(vc -> this.visitCount = vc);
        return this;
    }

    /**
     * Sets the {@code FavouriteItem} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withFavouriteItem(String favouriteItem) {
        this.favouriteItem = new FavouriteItem(favouriteItem);
        return this;
    }

    /**
     * Sets the {@code FavouriteItem} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withFavouriteItem(FavouriteItem favouriteItem) {
        this.favouriteItem = favouriteItem;
        return this;
    }

    /**
     * Sets the {@code FavouriteItem} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalFavouriteItem(Optional<FavouriteItem> favouriteItemOptional) {
        favouriteItemOptional.ifPresent(fi -> this.favouriteItem = fi);
        return this;
    }

    /**
     * Sets the {@code TotalSpent} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withTotalSpent(String totalSpent) {
        this.totalSpent = new TotalSpent(totalSpent);
        return this;
    }

    /**
     * Sets the {@code TotalSpent} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withTotalSpent(TotalSpent totalSpent) {
        this.totalSpent = totalSpent;
        return this;
    }

    /**
     * Sets the {@code TotalSpent} of the {@code Customer} if present in the Optional.
     */
    public CustomerBuilder withOptionalTotalSpent(Optional<TotalSpent> totalSpentOptional) {
        totalSpentOptional.ifPresent(ts -> this.totalSpent = ts);
        return this;
    }

    /**
     * Builds the customer with the information altogether.
     */
    public Customer build() {
        return new Customer(name, phone, email, address, remark, tags,
                customerId, rewardPoints, visitCount, favouriteItem, totalSpent);
    }
}
