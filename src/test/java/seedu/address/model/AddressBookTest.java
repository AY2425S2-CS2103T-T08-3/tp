package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStaff.BEN;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.StaffBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);

        // Two staffs with the same identity fields
        Staff editedBen = new StaffBuilder(BEN).withTags(VALID_TAG_FRIEND).build();
        List<Staff> newStaffs = Arrays.asList(BEN, editedBen);

        // Two customers witht he same identity fields
        Customer editedOlivia = new CustomerBuilder(OLIVIA).withAddress(VALID_ADDRESS_OLIVIA)
                .withTags(VALID_TAG_STUDENT)
                .build();
        List<Customer> newCustomers = Arrays.asList(OLIVIA, editedOlivia);

        AddressBookStub newData = new AddressBookStub(newPersons, newStaffs, newCustomers);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void setStaff_nullTargetStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setStaff(null, BEN));
    }

    @Test
    public void setStaff_nullEditedStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.setStaff(BEN, null));
    }

    @Test
    public void setStaff_validStaff_success() {
        addressBook.addStaff(BEN);
        Staff editedBen = new StaffBuilder(BEN).withTags(VALID_TAG_FRIEND).build();
        addressBook.setStaff(BEN, editedBen);
        assertTrue(addressBook.hasPerson(editedBen));
    }

    @Test
    public void hasPerson_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_staffNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(BEN));
    }

    @Test
    public void hasPerson_staffInAddressBook_returnsTrue() {
        addressBook.addStaff(BEN);
        assertTrue(addressBook.hasPerson(BEN));
    }

    @Test
    public void addStaff_validStaff_success() {
        addressBook.addStaff(BEN);
        assertTrue(addressBook.hasPerson(BEN));
    }

    @Test
    public void removeStaff_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeStaff(null));
    }

    @Test
    public void removeStaff_staffInAddressBook_removesSuccessfully() {
        addressBook.addStaff(BEN);
        addressBook.removeStaff(BEN);
        assertFalse(addressBook.hasPerson(BEN));
    }

    @Test
    public void hasPerson_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_customerNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(JAMES));
    }

    @Test
    public void hasPerson_customerInAddressBook_returnsTrue() {
        addressBook.addCustomer(JAMES);
        assertTrue(addressBook.hasPerson(JAMES));
    }

    @Test
    public void getCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                addressBook.getCustomerList().remove(0));
    }


    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Staff> staffs = FXCollections.observableArrayList();
        private final ObservableList<Customer> customers = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Staff> staff, Collection<Customer> customer) {
            this.persons.setAll(persons);
            this.staffs.setAll(staff);
            this.customers.setAll(customers);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Staff> getStaffList() {
            return staffs;
        }

        @Override
        public ObservableList<Customer> getCustomerList() {
            return customers;
        }

    }

}
