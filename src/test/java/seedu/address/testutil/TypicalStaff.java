package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Staff;
import seedu.address.model.util.StaffBuilder;

/**
 * A utility class containing a list of {@code Staff} objects to be used in tests.
 */
public class TypicalStaff {

    public static final Staff ALEX = new StaffBuilder().withName("Alex Tan").withPhone("91234567")
            .withEmail("alex.tan@example.com").withAddress("456, Bukit Timah Road, #12-34")
            .withStaffId("S1001").withRole("Barista").withShiftTiming("8am-4pm")
            .withHoursWorked("40").withPerformanceRating("4.5").withTags("teamplayer").build();

    public static final Staff BEN = new StaffBuilder().withName("Ben Chua").withPhone("98765432")
            .withEmail("ben.chua@example.com").withAddress("789, Orchard Road, #22-56")
            .withStaffId("S1002").withRole("Cashier").withShiftTiming("4pm-12am")
            .withHoursWorked("38").withPerformanceRating("4.2").withTags("punctual").build();

    private TypicalStaff() {} // prevents instantiation

    public static List<Staff> getTypicalStaff() {
        return new ArrayList<>(Arrays.asList(ALEX, BEN));
    }

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Staff staff : getTypicalStaff()) {
            ab.addStaff(staff);
        }
        return ab;
    }
}
