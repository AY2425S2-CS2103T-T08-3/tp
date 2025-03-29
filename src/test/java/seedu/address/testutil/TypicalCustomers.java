package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Customer;
import seedu.address.model.util.CustomerBuilder;

/**
 * A utility class containing a list of {@code Customer} objects to be used in tests.
 */
public class TypicalCustomers {

    public static final Customer JAMES = new CustomerBuilder().withName("James Wilson")
        .withCustomerId("C10012")
        .withAddress("45 Orchard Road, #12-34")
        .withEmail("james@example.com")
        .withPhone("91234789")
        .withRemark("Prefers decaf")
        .withRewardPoints("200")
        .withVisitCount("12")
        .withFavouriteItem("Flat White")
        .withTotalSpent("155.75")
        .withTags("regular", "student").build();

    public static final Customer OLIVIA = new CustomerBuilder().withName("Olivia Chen")
            .withCustomerId("C12345")
            .withAddress("88 Sunset Drive, #05-10")
            .withEmail("olivia@example.com")
            .withPhone("82345678")
            .withRemark("Birthday in March")
            .withRewardPoints("75")
            .withVisitCount("3")
            .withFavouriteItem("Green Tea Latte")
            .withTotalSpent("42.90")
            .withTags("new", "student").build();

    private TypicalCustomers() {} // prevents instantiation

    /**
     * Returns a list of all typical customers for testing.
     */
    public static List<Customer> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(JAMES, OLIVIA));
    }

    /**
     * Returns a new {@code AddressBook} with all the typical customers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Customer customer : getTypicalCustomers()) {
            ab.addCustomer(customer);
        }
        return ab;
    }
}
