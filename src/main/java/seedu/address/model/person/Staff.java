package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Staff member in the café management system.
 * Inherits from Person and adds staff-specific attributes.
 */
public class Staff extends Person {

    // Staff-specific fields
    private final StaffId staffId; // Unique identifier for staff
    private final Role role; // Job role (e.g., "Barista", "Manager")
    private final ShiftTiming shiftTiming; // Work schedule (e.g., "9am-5pm")
    private final HoursWorked hoursWorked; // Total hours worked in a period
    private final PerformanceRating performanceRating; // Performance rating out of 5.0

    /**
     * Every field must be present and not null.
     */
    public Staff(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags, StaffId staffId,
                 Role role, ShiftTiming shiftTiming, HoursWorked hoursWorked, PerformanceRating performanceRating) {
        super(name, phone, email, address, remark, tags);
        requireAllNonNull(staffId, role, shiftTiming, hoursWorked, performanceRating);

        this.staffId = staffId;
        this.role = role;
        this.shiftTiming = shiftTiming;
        this.hoursWorked = hoursWorked;
        this.performanceRating = performanceRating;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public Role getRole() {
        return role;
    }

    public ShiftTiming getShiftTiming() {
        return shiftTiming;
    }

    public HoursWorked getHoursWorked() {
        return hoursWorked;
    }

    public PerformanceRating getPerformanceRating() {
        return performanceRating;
    }

    /**
     * Returns true if both staff members have the same staff ID.
     * This defines a weaker notion of equality between two staff members.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        if (!(otherPerson instanceof Staff)) {
            return false;
        }

        Staff otherStaff = (Staff) otherPerson;
        return otherStaff.getStaffId().equals(getStaffId());
    }

    /**
     * Returns true if both staff members have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Staff)) {
            return false;
        }

        Staff otherPerson = (Staff) other;
        return super.equals(otherPerson) // Calls the equality check from Person
                && staffId.equals(otherPerson.staffId)
                && role.equals(otherPerson.role)
                && shiftTiming.equals(otherPerson.shiftTiming)
                && hoursWorked.equals(otherPerson.hoursWorked)
                && performanceRating.equals(otherPerson.performanceRating);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(staffId, role, shiftTiming, hoursWorked, performanceRating);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("staffId", getStaffId())
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("tags", getTags())
                .add("role", getRole())
                .add("shiftTiming", getShiftTiming())
                .add("hoursWorked", getHoursWorked())
                .add("performanceRating", getPerformanceRating())
                .toString();
    }

}
