package seedu.address.model;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Staff;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    FilteredList<Staff> getStaffList();

    /**
     * Returns an unmodifiable view of the customer list.
     * This list will not contain any duplicate customers.
     */
    FilteredList<Customer> getCustomerList();
}
