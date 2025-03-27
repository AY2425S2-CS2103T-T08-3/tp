package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStaff.ALEX;
import static seedu.address.testutil.TypicalStaff.BEN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateStaffException;
import seedu.address.model.person.exceptions.StaffNotFoundException;
import seedu.address.testutil.StaffBuilder;

public class UniqueStaffListTest {

    private final UniqueStaffList uniqueStaffList = new UniqueStaffList();

    @Test
    public void contains_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStaffList.contains(null));
    }

    @Test
    public void contains_staffNotInList_returnsFalse() {
        assertFalse(uniqueStaffList.contains(ALEX));
    }

    @Test
    public void contains_staffInList_returnsTrue() {
        uniqueStaffList.add(ALEX);
        assertTrue(uniqueStaffList.contains(ALEX));
    }

    @Test
    public void contains_staffWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStaffList.add(ALEX);
        Staff editedAlex = new StaffBuilder(ALEX).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueStaffList.contains(editedAlex));
    }

    @Test
    public void add_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStaffList.add(null));
    }

    @Test
    public void add_duplicateStaff_throwsDuplicateStaffException() {
        uniqueStaffList.add(ALEX);
        assertThrows(DuplicateStaffException.class, () -> uniqueStaffList.add(ALEX));
    }

    @Test
    public void setStaff_nullTargetStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStaffList.setStaff(null, ALEX));
    }

    @Test
    public void setStaff_nullEditedStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStaffList.setStaff(ALEX, null));
    }

    @Test
    public void setStaff_targetStaffNotInList_throwsStaffNotFoundException() {
        assertThrows(StaffNotFoundException.class, () -> uniqueStaffList.setStaff(ALEX, ALEX));
    }

    @Test
    public void setStaff_editedStaffisSamePerson_success() {
        uniqueStaffList.add(ALEX);
        uniqueStaffList.setStaff(ALEX, ALEX);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(ALEX);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasSameIdentity_success() {
        uniqueStaffList.add(ALEX);
        Staff editedAlex = new StaffBuilder(ALEX).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        uniqueStaffList.setStaff(ALEX, editedAlex);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(editedAlex);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasDifferentIdentity_success() {
        uniqueStaffList.add(ALEX);
        uniqueStaffList.setStaff(ALEX, BEN);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(BEN);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasNonUniqueIdentity_throwsDuplicateStaffException() {
        uniqueStaffList.add(ALEX);
        uniqueStaffList.add(BEN);
        assertThrows(DuplicateStaffException.class, () -> uniqueStaffList.setStaff(ALEX, BEN));
    }

    @Test
    public void remove_nullStaff_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStaffList.remove(null));
    }

    @Test
    public void remove_staffDoesNotExist_throwsStaffNotFoundException() {
        assertThrows(StaffNotFoundException.class, () -> uniqueStaffList.remove(ALEX));
    }

    @Test
    public void remove_existingStaff_removesStaff() {
        uniqueStaffList.add(ALEX);
        uniqueStaffList.remove(ALEX);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_nullUniqueStaffList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStaffList.setStaffs((UniqueStaffList) null));
    }

    @Test
    public void setStaffs_uniqueStaffList_replacesOwnListWithProvidedUniqueStaffList() {
        uniqueStaffList.add(ALEX);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(BEN);
        uniqueStaffList.setStaffs(expectedUniqueStaffList);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStaffList.setStaffs((List<Staff>) null));
    }

    @Test
    public void setStaffs_list_replacesOwnListWithProvidedList() {
        uniqueStaffList.add(ALEX);
        List<Staff> staffList = Collections.singletonList(BEN);
        uniqueStaffList.setStaffs(staffList);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(BEN);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_listWithDuplicateStaff_throwsDuplicateStaffException() {
        List<Staff> listWithDuplicateStaff = Arrays.asList(ALEX, ALEX);
        assertThrows(DuplicateStaffException.class, () -> uniqueStaffList.setStaffs(listWithDuplicateStaff));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueStaffList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueStaffList.asUnmodifiableObservableList().toString(), uniqueStaffList.toString());
    }
}
