package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CustomerBuilder;

public class CustomerTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Customer customer = new CustomerBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> customer.getTags().remove(0));
    }

    @Test
    public void isSameCustomer() {
        // same object -> returns true
        assertTrue(ALICE.isSameCustomer(ALICE));

        // Different phone, rest same -> returns false
        Customer editedAlice = new CustomerBuilder(ALICE).withPhone("91234567").build();
        assertFalse(ALICE.isSameCustomer(editedAlice));
    }

    @Test
    public void hashCode_sameCustomer_returnsSameHashCode() {
        Customer aliceCopy = new CustomerBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode()); // Same fields -> same hashcode
    }

    @Test
    public void hashCode_differentCustomer_returnsDifferentHashCode() {
        Customer editedAlice = new CustomerBuilder(ALICE).withRewardPoints(999).build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode()); // Different reward points -> different hashcode
    }

    @Test
    public void isSameCustomer_nullCustomer_returnsFalse() {
        assertFalse(BOB.isSameCustomer(null)); // null -> should return false
    }

    @Test
    public void equals() {
        // same values -> returns true
        Customer aliceCopy = new CustomerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different customer -> returns false
        assertFalse(ALICE.equals(BOB));

        // different reward points -> returns false
        Customer editedAlice = new CustomerBuilder(ALICE).withRewardPoints(1000).build();
        assertFalse(ALICE.equals(editedAlice));

        // different visit count -> returns false
        editedAlice = new CustomerBuilder(ALICE).withVisitCount(20).build();
        assertFalse(ALICE.equals(editedAlice));

        // different favorite item -> returns false
        editedAlice = new CustomerBuilder(ALICE).withFavoriteItem("Espresso").build();
        assertFalse(ALICE.equals(editedAlice));

        // different total spent -> returns false
        editedAlice = new CustomerBuilder(ALICE).withTotalSpent(999.99).build();
        assertFalse(ALICE.equals(editedAlice));

        // different rating -> returns false
        editedAlice = new CustomerBuilder(ALICE).withRating(2).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Customer.class.getCanonicalName() + "{name=" + ALICE.getName() + ", "
                + "phone=" + ALICE.getPhone() + ", email=" + ALICE.getEmail()
                + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", rewardPoints=" + ALICE.getRewardPoints() + ", visitCount=" + ALICE.getVisitCount()
                + ", favoriteItem=" + ALICE.getFavoriteItem() + ", totalSpent=" + ALICE.getTotalSpent()
                + ", rating=" + ALICE.getRating() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
