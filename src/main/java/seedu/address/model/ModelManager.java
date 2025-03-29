package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Staff> filteredStaffs;
    private final FilteredList<Customer> filteredCustomers;
    private final DrinkCatalog drinkCatalog;
    private final FilteredList<Drink> filteredDrinks;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyDrinkCatalog drinkCatalog) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.drinkCatalog = new DrinkCatalog(drinkCatalog);
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredStaffs = new FilteredList<>(this.addressBook.getStaffList());
        filteredCustomers = new FilteredList<>(this.addressBook.getCustomerList());
        filteredDrinks = new FilteredList<>(this.drinkCatalog.getDrinkList());;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new DrinkCatalog());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasStaff(Staff staffMember) {
        requireAllNonNull(staffMember);
        return addressBook.hasStaff(staffMember);
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return addressBook.hasCustomer(customer);
    }


    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void updateFilteredStaffList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
    }
    @Override
    public void updateFilteredCustomerList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    @Override
    public void addStaff(Staff staffMember) {
        addressBook.addStaff(staffMember);
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
    }

    @Override
    public void deleteStaff(Staff staffMember) {
        requireNonNull(staffMember);
        addressBook.removeStaff(staffMember);
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
    }

    @Override
    public void addCustomer(Customer customer) {
        addressBook.addCustomer(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        requireNonNull(customer);
        addressBook.removeCustomer(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }


    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setStaff(Staff target, Staff editedStaff) {
        requireAllNonNull(target, editedStaff);

        addressBook.setStaff(target, editedStaff);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        addressBook.setCustomer(target, editedCustomer);
    }
    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return filteredStaffs;
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && drinkCatalog.equals(otherModelManager.drinkCatalog)
                && filteredDrinks.equals(otherModelManager.filteredDrinks);
    }

    @Override
    public boolean hasDrink(Drink drink) {
        requireNonNull(drink);
        return drinkCatalog.hasDrink(drink);
    }

    @Override
    public void addDrink(Drink drink) {
        drinkCatalog.addDrink(drink);
        updateFilteredDrinkList(PREDICATE_SHOW_ALL_DRINKS);
    }

    @Override
    public void deleteDrink(Drink target) {
        drinkCatalog.removeDrink(target);
    }

    @Override
    public void setDrink(Drink target, Drink editedDrink) {
        requireAllNonNull(target, editedDrink);
        drinkCatalog.setDrink(target, editedDrink);
    }

    @Override
    public ObservableList<Drink> getFilteredDrinkList() {
        return filteredDrinks;
    }

    @Override
    public void updateFilteredDrinkList(Predicate<Drink> predicate) {
        requireNonNull(predicate);
        filteredDrinks.setPredicate(predicate);
    }
    @Override
    public ReadOnlyDrinkCatalog getDrinkCatalog() {
        return drinkCatalog;
    }

    @Override
    public Path getDrinkCatalogFilePath() {
        return userPrefs.getDrinkCatalogFilePath();
    }

    @Override
    public void setDrinkCatalogFilePath(Path drinkCatalogFilePath) {
        requireNonNull(drinkCatalogFilePath);
        userPrefs.setDrinkCatalogFilePath(drinkCatalogFilePath);
    }

    @Override
    public void setDrinkCatalog(ReadOnlyDrinkCatalog drinkCatalog) {
        this.drinkCatalog.resetData(drinkCatalog);
    }

}
