package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.DrinkCatalog;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.drink.Category;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.DrinkName;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.ReadOnlyDrinkCatalog;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.HoursWorked;
import seedu.address.model.person.Name;
import seedu.address.model.person.PerformanceRating;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.Role;
import seedu.address.model.person.ShiftTiming;
import seedu.address.model.person.Staff;
import seedu.address.model.person.StaffId;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(
                    new Name("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    EMPTY_REMARK,
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), EMPTY_REMARK,
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_REMARK,
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), EMPTY_REMARK,
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), EMPTY_REMARK,
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), EMPTY_REMARK,
                    getTagSet("colleagues"))
        };
    }

    public static Staff[] getSampleStaff() {
        return new Staff[] {
            new Staff(
                    new Name("Alice Tan"),
                    new Phone("81234567"),
                    new Email("alice.tan@cafeexample.com"),
                    new Address("123 Café Street"),
                    new Remark("Punctual and friendly"),
                    getTagSet("barista", "fulltime"),
                    new StaffId("S001"),
                    new Role("Barista"),
                    new ShiftTiming("Morning Shift"),
                    new HoursWorked("40"),
                    new PerformanceRating("4.8")
            ),
            new Staff(
                    new Name("Bob Lim"),
                    new Phone("87654321"),
                    new Email("bob.lim@cafeexample.com"),
                    new Address("456 Coffee Lane"),
                    new Remark("Great leadership skills"),
                    getTagSet("manager", "fulltime"),
                    new StaffId("S002"),
                    new Role("Manager"),
                    new ShiftTiming("Day Shift"),
                    new HoursWorked("45"),
                    new PerformanceRating("4.9")
            ),
            new Staff(
                    new Name("Charlie Wong"),
                    new Phone("89898989"),
                    new Email("charlie.wong@cafeexample.com"),
                    new Address("789 Espresso Avenue"),
                    new Remark("Creative and efficient"),
                    getTagSet("chef", "parttime"),
                    new StaffId("S003"),
                    new Role("Chef"),
                    new ShiftTiming("Evening Shift"),
                    new HoursWorked("25"),
                    new PerformanceRating("4.5")
            )
        };
    }

    public static Customer[] getSampleCustomers() {
        return new Customer[] {
            new Customer(
                    new Name("Abby Tan"),
                    new Phone("91234567"),
                    new Email("abby.tan@cafeexample.com"),
                    new Address("123 Café Street"),
                    new Remark("Always comes at 7am!"),
                    getTagSet(),
                    new CustomerId("C001"),
                    new RewardPoints("100"),
                    new VisitCount("12"),
                    new Drink("latte"),
                    new TotalSpent("120.3")
            ),
            new Customer(
                    new Name("Bella Smith"),
                    new Phone("81234567"),
                    new Email("bellasmith@cafeexample.com"),
                    new Address("123 Café Street"),
                    new Remark("comes with her dog"),
                    getTagSet(),
                    new CustomerId("C002"),
                    new RewardPoints("100"),
                    new VisitCount("12"),
                    new Drink("latte"),
                    new TotalSpent("120.3")
            ),
            new Customer(
                    new Name("Cory"),
                    new Phone("81111111"),
                    new Email("cory@cafeexample.com"),
                    new Address("123 Café Street"),
                    new Remark("interesting..."),
                    getTagSet(),
                    new CustomerId("C003"),
                    new RewardPoints("0"),
                    new VisitCount("78"),
                    new Drink("latte"),
                    new TotalSpent("50.0")
            )
        };
    }

    /**
     * Returns a sample DrinkCatalog with pre-loaded sample data.
     */
    public static ReadOnlyDrinkCatalog getSampleDrinkCatalog() {
        DrinkCatalog sampleDc = new DrinkCatalog();
        for (Drink sampleDrink : getSampleDrinks()) {
            sampleDc.addDrink(sampleDrink);
        }
        return sampleDc;
    }

    /**
     * Returns a list of sample drinks.
     */
    public static Drink[] getSampleDrinks() {
        return new Drink[] {
            new Drink(new DrinkName("Espresso"), new Price(3.50), new Category("Coffee")),
            new Drink(new DrinkName("Cappuccino"), new Price(4.50), new Category("Coffee")),
            new Drink(new DrinkName("Latte"), new Price(4.50), new Category("Coffee")),
            new Drink(new DrinkName("Mocha"), new Price(5.00), new Category("Coffee")),
            new Drink(new DrinkName("Green Tea"), new Price(3.00), new Category("Tea")),
            new Drink(new DrinkName("Black Tea"), new Price(3.00), new Category("Tea")),
            new Drink(new DrinkName("Earl Grey"), new Price(3.50), new Category("Tea")),
            new Drink(new DrinkName("Chamomile"), new Price(3.50), new Category("Tea")),
            new Drink(new DrinkName("Orange Juice"), new Price(4.00), new Category("Cold Drinks")),
            new Drink(new DrinkName("Apple Juice"), new Price(4.00), new Category("Cold Drinks")),
            new Drink(new DrinkName("Iced Coffee"), new Price(4.50), new Category("Cold Drinks")),
            new Drink(new DrinkName("Chocolate Chip Cookie"), new Price(2.50), new Category("Pastries")),
            new Drink(new DrinkName("Croissant"), new Price(3.00), new Category("Pastries")),
            new Drink(new DrinkName("Blueberry Muffin"), new Price(3.50), new Category("Pastries"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Staff sampleStaff : getSampleStaff()) {
            sampleAb.addStaff(sampleStaff);
        }
        for (Customer sampleCustomer : getSampleCustomers()) {
            sampleAb.addCustomer(sampleCustomer);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
