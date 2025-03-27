package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalDrinks.LATTE;
import static seedu.address.testutil.TypicalDrinks.getTypicalDrinkCatalog;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalStaff.ALEX;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.Assert;

public class ModelManagerTest {

    private ModelManager modelManager;
    private AddressBook addressBook;
    private UserPrefs userPrefs;
    private DrinkCatalog drinkCatalog;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
        addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        userPrefs = new UserPrefs();
        drinkCatalog = new DrinkCatalog();
    }

    @Test
    public void constructor_defaultValues_correct() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new DrinkCatalog(), new DrinkCatalog(modelManager.getDrinkCatalog()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs updatedUserPrefs = new UserPrefs();
        updatedUserPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        updatedUserPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));

        modelManager.setUserPrefs(updatedUserPrefs);
        assertEquals(updatedUserPrefs, modelManager.getUserPrefs());

        // Verify immutability
        UserPrefs originalUserPrefs = new UserPrefs(updatedUserPrefs);
        updatedUserPrefs.setAddressBookFilePath(Paths.get("new/path"));
        assertEquals(originalUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setFilePaths_nullInput_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setDrinkCatalogFilePath(null));
    }

    @Test
    public void setFilePaths_validInput_setsFilePaths() {
        Path addressBookPath = Paths.get("address/book/file/path");
        Path drinkCatalogPath = Paths.get("drink/catalog/file/path");

        modelManager.setAddressBookFilePath(addressBookPath);
        modelManager.setDrinkCatalogFilePath(drinkCatalogPath);

        assertEquals(addressBookPath, modelManager.getAddressBookFilePath());
        assertEquals(drinkCatalogPath, modelManager.getDrinkCatalogFilePath());
    }

    @Test
    public void setDrinkCatalog_nullInput_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setDrinkCatalog(null));
    }

    @Test
    public void setDrinkCatalog_validInput_setsDrinkCatalog() {
        DrinkCatalog updatedDrinkCatalog = new DrinkCatalog();
        updatedDrinkCatalog.addDrink(LATTE);

        modelManager.setDrinkCatalog(updatedDrinkCatalog);
        assertEquals(updatedDrinkCatalog, modelManager.getDrinkCatalog());
    }

    @Test
    public void hasPerson_nullOrNonExistentPerson_handledCorrectly() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
        assertFalse(modelManager.hasPerson(ALICE));

        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_nullOrNonExistentStaff_handledCorrectly() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
        assertFalse(modelManager.hasPerson(ALEX));

        modelManager.addStaff(ALEX);
        assertTrue(modelManager.hasPerson(ALEX));
    }

    @Test
    public void hasDrink_nullOrNonExistentDrink_handledCorrectly() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasDrink(null));
        assertFalse(modelManager.hasDrink(LATTE));

        modelManager.addDrink(LATTE);
        assertTrue(modelManager.hasDrink(LATTE));
    }

    @Test
    public void filteredLists_modifyAttempt_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStaffList().remove(0));
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDrinkList().remove(0));
    }

    @Test
    public void equals_differentScenarios_correctComparison() {
        ModelManager initialModelManager = new ModelManager(addressBook, userPrefs, getTypicalDrinkCatalog());
        ModelManager identicalModelManager = new ModelManager(addressBook, userPrefs, getTypicalDrinkCatalog());

        // Equality checks
        assertTrue(initialModelManager.equals(initialModelManager)); // Same object
        assertTrue(initialModelManager.equals(identicalModelManager)); // Same content
        assertFalse(initialModelManager.equals(null)); // Null comparison
        assertFalse(initialModelManager.equals(new Object())); // Different type

        // Different address book
        AddressBook differentAddressBook = new AddressBook();
        assertFalse(initialModelManager.equals(
                new ModelManager(differentAddressBook, userPrefs, getTypicalDrinkCatalog())
        ));

        // Different user preferences
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("different/path"));
        assertFalse(initialModelManager.equals(
                new ModelManager(addressBook, differentUserPrefs, getTypicalDrinkCatalog())
        ));

        // Filtered list comparison
        ModelManager filteredModelManager = new ModelManager(addressBook, userPrefs, getTypicalDrinkCatalog());
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        filteredModelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        assertFalse(initialModelManager.equals(filteredModelManager));
    }
}
