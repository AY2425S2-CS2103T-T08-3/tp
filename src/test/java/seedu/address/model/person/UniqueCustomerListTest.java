package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.JAMES;
import static seedu.address.testutil.TypicalCustomers.OLIVIA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.CustomerBuilder;

public class UniqueCustomerListTest {

    private final UniqueCustomerList uniqueCustomerList = new UniqueCustomerList();

    @Test
    public void contains_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.contains(null));
    }

    @Test
    public void contains_customerNotInList_returnsFalse() {
        assertFalse(uniqueCustomerList.contains(JAMES));
    }

    @Test
    public void contains_customerInList_returnsTrue() {
        uniqueCustomerList.add(JAMES);
        assertTrue(uniqueCustomerList.contains(JAMES));
    }

    @Test
    public void add_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.add(null));
    }

    @Test
    public void add_duplicateCustomer_throwsDuplicatePersonException() {
        uniqueCustomerList.add(JAMES);
        assertThrows(DuplicatePersonException.class, () -> uniqueCustomerList.add(JAMES));
    }

    @Test
    public void setPerson_nullTargetCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPerson(null, JAMES));
    }

    @Test
    public void setPerson_nullEditedCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPerson(JAMES, null));
    }

    @Test
    public void setPerson_targetCustomerNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueCustomerList.setPerson(JAMES, JAMES));
    }

    @Test
    public void setPerson_editedCustomerisSamePerson_success() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.setPerson(JAMES, JAMES);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(JAMES);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPerson_editedCustomerHasSameIdentity_success() {
        uniqueCustomerList.add(JAMES);
        Customer editedJames = new CustomerBuilder(JAMES).withAddress("different address").withRewardPoints("300")
                .withTags("regular").build();
        uniqueCustomerList.setPerson(JAMES, editedJames);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(editedJames);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPerson_editedCustomerHasDifferentIdentity_success() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.setPerson(JAMES, OLIVIA);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(OLIVIA);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPerson_editedCustomerHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.add(OLIVIA);
        assertThrows(DuplicatePersonException.class, () -> uniqueCustomerList.setPerson(JAMES, OLIVIA));
    }

    @Test
    public void remove_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.remove(null));
    }

    @Test
    public void remove_customerDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueCustomerList.remove(JAMES));
    }

    @Test
    public void remove_existingCustomer_removesCustomer() {
        uniqueCustomerList.add(JAMES);
        uniqueCustomerList.remove(JAMES);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPersons_nullUniqueCustomerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPersons((UniqueCustomerList) null));
    }

    @Test
    public void setPersons_uniqueCustomerList_replacesOwnListWithProvidedUniqueCustomerList() {
        uniqueCustomerList.add(JAMES);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(OLIVIA);
        uniqueCustomerList.setPersons(expectedUniqueCustomerList);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPersons((List<Customer>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueCustomerList.add(JAMES);
        List<Customer> customerList = Collections.singletonList(OLIVIA);
        uniqueCustomerList.setPersons(customerList);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(OLIVIA);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPersons_listWithDuplicateCustomers_throwsDuplicatePersonException() {
        List<Customer> listWithDuplicateCustomers = Arrays.asList(JAMES, JAMES);
        assertThrows(DuplicatePersonException.class, () -> uniqueCustomerList.setPersons(listWithDuplicateCustomers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueCustomerList.asUnmodifiableObservableList().remove(0));
    }
}
