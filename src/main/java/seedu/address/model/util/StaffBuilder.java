package seedu.address.model.util;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Name;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Staff objects.
 */
public class StaffBuilder {

    public static final String DEFAULT_NAME = "John Doe";
    public static final String DEFAULT_PHONE = "12345678";
    public static final String DEFAULT_EMAIL = "johndoe@example.com";
    public static final String DEFAULT_ADDRESS = "123 Main Street";
    public static final String DEFAULT_REMARK = "No remarks";
    public static final String DEFAULT_STAFF_ID = "S1001";
    public static final String DEFAULT_ROLE = "Barista";
    public static final String DEFAULT_SHIFT_TIMING = "8am-4pm";
    public static final String DEFAULT_HOURS_WORKED = "40";
    public static final String DEFAULT_PERFORMANCE_RATING = "4.5";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;
    private StaffId staffId;
    private Role role;
    private ShiftTiming shiftTiming;
    private HoursWorked hoursWorked;
    private PerformanceRating performanceRating;

    /**
     * Creates a {@code StaffBuilder} with the default details.
     */
    public StaffBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        staffId = new StaffId(DEFAULT_STAFF_ID);
        role = new Role(DEFAULT_ROLE);
        shiftTiming = new ShiftTiming(DEFAULT_SHIFT_TIMING);
        hoursWorked = new HoursWorked(DEFAULT_HOURS_WORKED);
        performanceRating = new PerformanceRating(DEFAULT_PERFORMANCE_RATING);
    }

    /**
     * Initializes the StaffBuilder with the data of {@code staffToCopy}.
     */
    public StaffBuilder(Staff staffToCopy) {
        name = staffToCopy.getName();
        phone = staffToCopy.getPhone();
        email = staffToCopy.getEmail();
        address = staffToCopy.getAddress();
        remark = staffToCopy.getRemark();
        tags = new HashSet<>(staffToCopy.getTags());
        staffId = staffToCopy.getStaffId();
        role = staffToCopy.getRole();
        shiftTiming = staffToCopy.getShiftTiming();
        hoursWorked = staffToCopy.getHoursWorked();
        performanceRating = staffToCopy.getPerformanceRating();
    }

    /**
     * Sets the {@code Name} of the {@code Staff} that we are building.
     */
    public StaffBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Staff} that we are building.
     */
    public StaffBuilder withName(Name name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalName(Optional<Name> nameOptional) {
        nameOptional.ifPresent(n -> this.name = n);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Staff} that we are building.
     */
    public StaffBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Staff} that we are building.
     */
    public StaffBuilder withPhone(Phone phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalPhone(Optional<Phone> phoneOptional) {
        phoneOptional.ifPresent(p -> this.phone = p);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Staff} that we are building.
     */
    public StaffBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Staff} that we are building.
     */
    public StaffBuilder withEmail(Email email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalEmail(Optional<Email> emailOptional) {
        emailOptional.ifPresent(e -> this.email = e);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Staff} that we are building.
     */
    public StaffBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Staff} that we are building.
     */
    public StaffBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalAddress(Optional<Address> addressOptional) {
        addressOptional.ifPresent(a -> this.address = a);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Staff} that we are building.
     */
    public StaffBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Staff} that we are building.
     */
    public StaffBuilder withRemark(Remark remark) {
        this.remark = remark;
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalRemark(Optional<Remark> remarkOptional) {
        remarkOptional.ifPresent(r -> this.remark = r);
        return this;
    }

    /**
     * Sets the {@code Tags} of the {@code Staff} that we are building.
     */
    public StaffBuilder withTags(Set<Tag> tags) {
        this.tags = new HashSet<>(tags);
        return this;
    }

    /**
     * Sets the {@code Tags} of the {@code Staff} that we are building.
     */
    public StaffBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Tags} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalTags(Optional<Set<Tag>> tagsOptional) {
        tagsOptional.ifPresent(t -> this.tags = t);
        return this;
    }

    /**
     * Sets the {@code StaffId} of the {@code Staff} that we are building.
     */
    public StaffBuilder withStaffId(String staffId) {
        this.staffId = new StaffId(staffId);
        return this;
    }

    /**
     * Sets the {@code StaffId} of the {@code Staff} that we are building.
     */
    public StaffBuilder withStaffId(StaffId staffId) {
        this.staffId = staffId;
        return this;
    }

    /**
     * Sets the {@code StaffId} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalStaffId(Optional<StaffId> staffIdOptional) {
        staffIdOptional.ifPresent(id -> this.staffId = id);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Staff} that we are building.
     */
    public StaffBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Staff} that we are building.
     */
    public StaffBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalRole(Optional<Role> roleOptional) {
        roleOptional.ifPresent(r -> this.role = r);
        return this;
    }

    /**
     * Sets the {@code ShiftTiming} of the {@code Staff} that we are building.
     */
    public StaffBuilder withShiftTiming(String shiftTiming) {
        this.shiftTiming = new ShiftTiming(shiftTiming);
        return this;
    }

    /**
     * Sets the {@code ShiftTiming} of the {@code Staff} that we are building.
     */
    public StaffBuilder withShiftTiming(ShiftTiming shiftTiming) {
        this.shiftTiming = shiftTiming;
        return this;
    }

    /**
     * Sets the {@code ShiftTiming} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalShiftTiming(Optional<ShiftTiming> shiftTimingOptional) {
        shiftTimingOptional.ifPresent(st -> this.shiftTiming = st);
        return this;
    }

    /**
     * Sets the {@code HoursWorked} of the {@code Staff} that we are building.
     */
    public StaffBuilder withHoursWorked(String hoursWorked) {
        this.hoursWorked = new HoursWorked(hoursWorked);
        return this;
    }

    /**
     * Sets the {@code HoursWorked} of the {@code Staff} that we are building.
     */
    public StaffBuilder withHoursWorked(HoursWorked hoursWorked) {
        this.hoursWorked = hoursWorked;
        return this;
    }

    /**
     * Sets the {@code HoursWorked} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalHoursWorked(Optional<HoursWorked> hoursWorkedOptional) {
        hoursWorkedOptional.ifPresent(hw -> this.hoursWorked = hw);
        return this;
    }

    /**
     * Sets the {@code PerformanceRating} of the {@code Staff} that we are building.
     */
    public StaffBuilder withPerformanceRating(String performanceRating) {
        this.performanceRating = new PerformanceRating(performanceRating);
        return this;
    }

    /**
     * Sets the {@code PerformanceRating} of the {@code Staff} that we are building.
     */
    public StaffBuilder withPerformanceRating(PerformanceRating performanceRating) {
        this.performanceRating = performanceRating;
        return this;
    }

    /**
     * Sets the {@code PerformanceRating} of the {@code Staff} if present in the Optional.
     */
    public StaffBuilder withOptionalPerformanceRating(Optional<PerformanceRating> performanceRatingOptional) {
        performanceRatingOptional.ifPresent(pr -> this.performanceRating = pr);
        return this;
    }

    /**
     * Builds the staff with the information altogether.
     */
    public Staff build() {
        return new Staff(name, phone, email, address, remark, tags,
                staffId, role, shiftTiming, hoursWorked, performanceRating);
    }
}
