package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_STAFF = "Staff list contains duplicate staff member(s).";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customer list contains duplicate customer(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedStaff> staff = new ArrayList<>();
    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and staff.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("staff") List<JsonAdaptedStaff> staff,
                                       @JsonProperty("customers") List<JsonAdaptedCustomer> customers) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (staff != null) {
            this.staff.addAll(staff);
        }
        if (customers != null) {
            this.customers.addAll(customers);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        for (Person person : source.getPersonList()) {
            persons.add(new JsonAdaptedPerson(person));
        }

        for (Staff staffMember : source.getStaffList()) {
            staff.add(new JsonAdaptedStaff(staffMember));
        }

        for (Customer customer : source.getCustomerList()) {
            customers.add(new JsonAdaptedCustomer(customer));
        }
    }


    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedStaff jsonAdaptedStaff : staff) {
            Staff staffMember = jsonAdaptedStaff.toModelType();
            if (addressBook.hasPerson(staffMember)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            addressBook.addPerson(staffMember);
        }

        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (addressBook.hasPerson(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            addressBook.addPerson(customer);
        }

        return addressBook;
    }
}
