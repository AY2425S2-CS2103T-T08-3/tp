package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_STAFFS = unused -> true;
    Predicate<Person> PREDICATE_SHOW_ALL_CUSTOMERS = unused -> true;
    Predicate<Drink> PREDICATE_SHOW_ALL_DRINKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Staff> getFilteredStaffList();

    /** Returns an unmodifiable view of the filtered customer list */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStaffList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     */
    void updateFilteredCustomerList(Predicate<Person> predicate);


    /**
     * Returns true if the drink catalog contains an equivalent drink as the given argument.
     */
    boolean hasDrink(Drink drink);

    /**
     * Adds the given drink.
     * The drink must not already exist in the drink catalog.
     */
    void addDrink(Drink drink);

    /**
     * Deletes the given drink.
     * The drink must exist in the drink catalog.
     */
    void deleteDrink(Drink target);

    /**
     * Replaces the given drink {@code target} with {@code editedDrink}.
     * {@code target} must exist in the drink catalog.
     * The drink identity of {@code editedDrink} must not be the same as another existing drink in the catalog.
     */
    void setDrink(Drink target, Drink editedDrink);

    /**
     * Returns an unmodifiable view of the filtered drink list
     */
    ObservableList<Drink> getFilteredDrinkList();

    /**
     * Updates the filter of the filtered drink list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDrinkList(Predicate<Drink> predicate);

    /**
     * Returns an unmodifiable view of the drink catalog
     */
    ReadOnlyDrinkCatalog getDrinkCatalog();

    /**
     * Returns the user prefs' drink catalog file path.
     */
    Path getDrinkCatalogFilePath();

    /**
     * Sets the user prefs' drink catalog file path.
     */
    void setDrinkCatalogFilePath(Path drinkCatalogFilePath);

    /**
     * Replaces drink catalog data with the data in {@code drinkCatalog}.
     */
    void setDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog);
}
