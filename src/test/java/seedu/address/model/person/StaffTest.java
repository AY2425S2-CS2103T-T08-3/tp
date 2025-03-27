package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StaffBuilder;

public class StaffTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Staff staff = new StaffBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> staff.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALEX.isSamePerson(ALEX));

        // Different staffId, same name, different phone, rest same -> returns false
        Staff editedBen = new StaffBuilder(BEN).withStaffId("S1005").withPhone("91234567").build();
        assertFalse(BEN.isSamePerson(editedBen));
    }

    @Test
    public void hashCode_sameStaff_returnsSameHashCode() {
        Staff benCopy = new StaffBuilder(BEN).build();
        assertEquals(BEN.hashCode(), benCopy.hashCode()); // Same fields -> same hashcode
    }

    @Test
    public void hashCode_differentStaff_returnsDifferentHashCode() {
        Staff editedBen = new StaffBuilder(BEN).withStaffId("S9999").build();
        assertNotEquals(BEN.hashCode(), editedBen.hashCode()); // Different staffId -> different hashcode
    }

    @Test
    public void isSamePerson_nullStaff_returnsFalse() {
        assertFalse(BEN.isSamePerson(null)); // null -> should return false
    }


    @Test
    public void equals() {
        // same values -> returns true
        Staff alexCopy = new StaffBuilder(ALEX).build();
        assertTrue(ALEX.equals(alexCopy));

        // same object -> returns true
        assertTrue(ALEX.equals(ALEX));

        // null -> returns false
        assertFalse(ALEX.equals(null));

        // different type -> returns false
        assertFalse(ALEX.equals(5));

        // different staff -> returns false
        assertFalse(ALEX.equals(BEN));

        // different staffId -> returns false
        Staff editedAlex = new StaffBuilder(ALEX).withStaffId("S1003").build();
        assertFalse(ALEX.equals(editedAlex));

        // different role -> returns false
        editedAlex = new StaffBuilder(ALEX).withRole("Manager").build();
        assertFalse(ALEX.equals(editedAlex));

        // different shiftTiming -> returns false
        editedAlex = new StaffBuilder(ALEX).withShiftTiming("10am-6pm").build();
        assertFalse(ALEX.equals(editedAlex));

        // different hoursWorked -> returns false
        editedAlex = new StaffBuilder(ALEX).withHoursWorked("45").build();
        assertFalse(ALEX.equals(editedAlex));

        // different performanceRating -> returns false
        editedAlex = new StaffBuilder(ALEX).withPerformanceRating("4.8").build();
        assertFalse(ALEX.equals(editedAlex));
    }

    @Test
    public void toStringMethod() {
        String expected = Staff.class.getCanonicalName() + "{staffId=" + ALEX.getStaffId() + ", "
                + "name=" + ALEX.getName() + ", phone=" + ALEX.getPhone() + ", email=" + ALEX.getEmail()
                + ", address=" + ALEX.getAddress() + ", tags=" + ALEX.getTags() + ", role=" + ALEX.getRole()
                + ", shiftTiming=" + ALEX.getShiftTiming() + ", hoursWorked=" + ALEX.getHoursWorked()
                + ", performanceRating=" + ALEX.getPerformanceRating() + "}";
        assertEquals(expected, ALEX.toString());
    }
}
