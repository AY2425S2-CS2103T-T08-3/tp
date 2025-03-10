package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Customer;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {


    public static final Customer ALICE = new CustomerBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withRemark("Regular customer")
            .withRewardPoints(150)
            .withVisitCount(8)
            .withFavoriteItem("Cappuccino")
            .withTotalSpent(120.50)
            .withRating(5)
            .withTags("regular", "vip").build();

    public static final Customer BOB = new CustomerBuilder().withName("Bob Choo")
            .withAddress("Block 123, Bobby Street 3")
            .withEmail("bob@example.com")
            .withPhone("98765432")
            .withRemark("New customer")
            .withRewardPoints(50)
            .withVisitCount(2)
            .withFavoriteItem("Americano")
            .withTotalSpent(35.20)
            .withRating(4)
            .withTags("new").build();

    private TypicalCustomers() {} // prevents instantiation

    /**
     * Returns a list of all typical customers for testing.
     */
    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(
                ALICE,
                BOB
                // Add other customers here
        ));
    }

    /**
     * Returns a new {@code AddressBook} with all the typical customers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Customer customer : getTypicalCustomers()) {
            ab.addPerson(customer);
        }
        return ab;
    }
}
