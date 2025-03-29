package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_DRINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS_WORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERFORMANCE_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAFF_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.descriptors.EditCustomerDescriptor;
import seedu.address.logic.parser.descriptors.EditPersonDescriptor;
import seedu.address.logic.parser.descriptors.EditStaffDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditCustomerDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditStaffDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    // '&' not allowed in names
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&";
    // 'a' not allowed in phones
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a";
    // missing '@' symbol
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo";
    // empty string not allowed for addresses
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS;
    // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*";
    // '@' not allowed in staff IDs
    public static final String INVALID_STAFF_ID_DESC = " " + PREFIX_STAFF_ID + "S12@34";
    // empty string not allowed for roles
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE;
    // invalid time format
    public static final String INVALID_SHIFT_DESC = " " + PREFIX_SHIFT_TIMING + "25pm-10pm";
    // negative hours not allowed
    public static final String INVALID_HOURS_DESC = " " + PREFIX_HOURS_WORKED + "-5";
    // rating out of range (1-5)
    public static final String INVALID_RATING_DESC = " " + PREFIX_PERFORMANCE_RATING + "6.0";
    // '#' not allowed in customer IDs
    public static final String INVALID_CUSTOMER_ID_DESC = " " + PREFIX_CUSTOMER_ID + "C12#45";
    // negative reward points not allowed
    public static final String INVALID_REWARD_POINTS_DESC = " " + PREFIX_REWARD_POINTS + "-100";
    // negative visit count not allowed
    public static final String INVALID_VISIT_COUNT_DESC = " " + PREFIX_VISIT_COUNT + "-1";
    // empty string not allowed for favourite drinks
    public static final String INVALID_FAVOURITE_DRINK_DESC = " " + PREFIX_FAVOURITE_DRINK;
    // negative total spent not allowed
    public static final String INVALID_TOTAL_SPENT_DESC = " " + PREFIX_TOTAL_SPENT + "-50.0";

    public static final String VALID_NAME_ALEX = "Alex Tan";
    public static final String VALID_NAME_BEN = "Ben Lim";
    public static final String VALID_PHONE_ALEX = "33333333";
    public static final String VALID_PHONE_BEN = "44444444";
    public static final String VALID_EMAIL_ALEX = "alex@example.com";
    public static final String VALID_EMAIL_BEN = "ben@example.com";
    public static final String VALID_ADDRESS_ALEX = "Block 456, Alex Road 2";
    public static final String VALID_ADDRESS_BEN = "Block 789, Ben Avenue 5";
    public static final String VALID_REMARK_ALEX = "Enjoys hiking.";
    public static final String VALID_REMARK_BEN = "Loves painting.";
    public static final String VALID_TAG_MANAGER = "manager";
    public static final String VALID_TAG_ENGINEER = "engineer";

    // Staff-specific fields
    public static final String VALID_STAFF_ID_ALEX = "S12345";
    public static final String VALID_STAFF_ID_BEN = "S67890";
    public static final String VALID_ROLE_ALEX = "Manager";
    public static final String VALID_ROLE_BEN = "Barista";
    public static final String VALID_SHIFT_ALEX = "9am-5pm";
    public static final String VALID_SHIFT_BEN = "2pm-10pm";
    public static final String VALID_HOURS_ALEX = "40";
    public static final String VALID_HOURS_BEN = "35";
    public static final String VALID_RATING_ALEX = "4.5";
    public static final String VALID_RATING_BEN = "4.2";

    // Description constants
    public static final String NAME_DESC_ALEX = " " + PREFIX_NAME + VALID_NAME_ALEX;
    public static final String NAME_DESC_BEN = " " + PREFIX_NAME + VALID_NAME_BEN;
    public static final String PHONE_DESC_ALEX = " " + PREFIX_PHONE + VALID_PHONE_ALEX;
    public static final String PHONE_DESC_BEN = " " + PREFIX_PHONE + VALID_PHONE_BEN;
    public static final String EMAIL_DESC_ALEX = " " + PREFIX_EMAIL + VALID_EMAIL_ALEX;
    public static final String EMAIL_DESC_BEN = " " + PREFIX_EMAIL + VALID_EMAIL_BEN;
    public static final String ADDRESS_DESC_ALEX = " " + PREFIX_ADDRESS + VALID_ADDRESS_ALEX;
    public static final String ADDRESS_DESC_BEN = " " + PREFIX_ADDRESS + VALID_ADDRESS_BEN;
    public static final String TAG_DESC_MANAGER = " " + PREFIX_TAG + VALID_TAG_MANAGER;
    public static final String TAG_DESC_ENGINEER = " " + PREFIX_TAG + VALID_TAG_ENGINEER;
    public static final String STAFF_ID_DESC_ALEX = " " + PREFIX_STAFF_ID + VALID_STAFF_ID_ALEX;
    public static final String STAFF_ID_DESC_BEN = " " + PREFIX_STAFF_ID + VALID_STAFF_ID_BEN;
    public static final String ROLE_DESC_ALEX = " " + PREFIX_ROLE + VALID_ROLE_ALEX;
    public static final String ROLE_DESC_BEN = " " + PREFIX_ROLE + VALID_ROLE_BEN;
    public static final String SHIFT_DESC_ALEX = " " + PREFIX_SHIFT_TIMING + VALID_SHIFT_ALEX;
    public static final String SHIFT_DESC_BEN = " " + PREFIX_SHIFT_TIMING + VALID_SHIFT_BEN;
    public static final String HOURS_DESC_ALEX = " " + PREFIX_HOURS_WORKED + VALID_HOURS_ALEX;
    public static final String HOURS_DESC_BEN = " " + PREFIX_HOURS_WORKED + VALID_HOURS_BEN;
    public static final String RATING_DESC_ALEX = " " + PREFIX_PERFORMANCE_RATING + VALID_RATING_ALEX;
    public static final String RATING_DESC_BEN = " " + PREFIX_PERFORMANCE_RATING + VALID_RATING_BEN;

    // Constants for James Wilson
    public static final String VALID_NAME_JAMES = "James Wilson";
    public static final String VALID_PHONE_JAMES = "91234789";
    public static final String VALID_EMAIL_JAMES = "james@example.com";
    public static final String VALID_ADDRESS_JAMES = "45 Orchard Road, #12-34";
    public static final String VALID_REMARK_JAMES = "Prefers decaf";
    public static final String VALID_TAG_STUDENT = "student";
    public static final String VALID_REWARD_POINTS_JAMES = "200";
    public static final String VALID_VISIT_COUNT_JAMES = "12";
    public static final String VALID_FAVOURITE_DRINK_JAMES = "Flat White";
    public static final String VALID_TOTAL_SPENT_JAMES = "155.75";
    public static final String VALID_RATING_JAMES = "5";
    public static final String VALID_CUSTOMER_ID_JAMES = "C45678";


    // Constants for Olivia Chen
    public static final String VALID_NAME_OLIVIA = "Olivia Chen";
    public static final String VALID_PHONE_OLIVIA = "82345678";
    public static final String VALID_EMAIL_OLIVIA = "olivia@example.com";
    public static final String VALID_ADDRESS_OLIVIA = "88 Sunset Drive, #05-10";
    public static final String VALID_REMARK_OLIVIA = "Birthday in March";
    public static final String VALID_TAG_NEW = "new";
    public static final String VALID_REWARD_POINTS_OLIVIA = "75";
    public static final String VALID_VISIT_COUNT_OLIVIA = "3";
    public static final String VALID_FAVOURITE_DRINK_OLIVIA = "Green Tea Latte";
    public static final String VALID_TOTAL_SPENT_OLIVIA = "42.90";
    public static final String VALID_CUSTOMER_ID_OLIVIA = "C12345";

    // Command string descriptors
    public static final String CUSTOMER_ID_DESC_OLIVIA = " " + PREFIX_CUSTOMER_ID + VALID_CUSTOMER_ID_OLIVIA;
    public static final String CUSTOMER_ID_DESC_JAMES = " " + PREFIX_CUSTOMER_ID + VALID_CUSTOMER_ID_JAMES;
    public static final String NAME_DESC_JAMES = " " + PREFIX_NAME + VALID_NAME_JAMES;
    public static final String NAME_DESC_OLIVIA = " " + PREFIX_NAME + VALID_NAME_OLIVIA;
    public static final String PHONE_DESC_JAMES = " " + PREFIX_PHONE + VALID_PHONE_JAMES;
    public static final String PHONE_DESC_OLIVIA = " " + PREFIX_PHONE + VALID_PHONE_OLIVIA;
    public static final String EMAIL_DESC_JAMES = " " + PREFIX_EMAIL + VALID_EMAIL_JAMES;
    public static final String EMAIL_DESC_OLIVIA = " " + PREFIX_EMAIL + VALID_EMAIL_OLIVIA;
    public static final String ADDRESS_DESC_JAMES = " " + PREFIX_ADDRESS + VALID_ADDRESS_JAMES;
    public static final String ADDRESS_DESC_OLIVIA = " " + PREFIX_ADDRESS + VALID_ADDRESS_OLIVIA;
    public static final String TAG_DESC_STUDENT = " " + PREFIX_TAG + VALID_TAG_STUDENT;
    public static final String TAG_DESC_NEW = " " + PREFIX_TAG + VALID_TAG_NEW;
    public static final String REWARD_POINTS_DESC_JAMES = " " + PREFIX_REWARD_POINTS + VALID_REWARD_POINTS_JAMES;
    public static final String REWARD_POINTS_DESC_OLIVIA = " " + PREFIX_REWARD_POINTS + VALID_REWARD_POINTS_OLIVIA;
    public static final String VISIT_COUNT_DESC_JAMES = " " + PREFIX_VISIT_COUNT + VALID_VISIT_COUNT_JAMES;
    public static final String VISIT_COUNT_DESC_OLIVIA = " " + PREFIX_VISIT_COUNT + VALID_VISIT_COUNT_OLIVIA;
    public static final String FAVOURITE_DRINK_DESC_JAMES = " " + PREFIX_FAVOURITE_DRINK + VALID_FAVOURITE_DRINK_JAMES;
    public static final String FAVOURITE_DRINK_DESC_OLIVIA = " " + PREFIX_FAVOURITE_DRINK + VALID_FAVOURITE_DRINK_OLIVIA;
    public static final String TOTAL_SPENT_DESC_JAMES = " " + PREFIX_TOTAL_SPENT + VALID_TOTAL_SPENT_JAMES;
    public static final String TOTAL_SPENT_DESC_OLIVIA = " " + PREFIX_TOTAL_SPENT + VALID_TOTAL_SPENT_OLIVIA;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;
    public static final EditStaffDescriptor DESC_ALEX;
    public static final EditStaffDescriptor DESC_BEN;
    public static final EditCustomerDescriptor DESC_JAMES;
    public static final EditCustomerDescriptor DESC_OLIVIA;

    // Drink-related constants
    public static final String VALID_NAME_LATTE = "Latte";
    public static final String VALID_NAME_CAPPUCCINO = "Cappuccino";
    public static final double VALID_PRICE_LATTE = 4.50;
    public static final double VALID_PRICE_CAPPUCCINO = 4.50;
    public static final String VALID_CATEGORY_COFFEE = "Coffee";
    public static final String VALID_CATEGORY_TEA = "Tea";

    public static final String NAME_DESC_LATTE = " " + PREFIX_NAME + VALID_NAME_LATTE;
    public static final String NAME_DESC_CAPPUCCINO = " " + PREFIX_NAME + VALID_NAME_CAPPUCCINO;
    public static final String PRICE_DESC_LATTE = " " + PREFIX_PRICE + VALID_PRICE_LATTE;
    public static final String PRICE_DESC_CAPPUCCINO = " " + PREFIX_PRICE + VALID_PRICE_CAPPUCCINO;
    public static final String CATEGORY_DESC_COFFEE = " " + PREFIX_CATEGORY + VALID_CATEGORY_COFFEE;
    public static final String CATEGORY_DESC_TEA = " " + PREFIX_CATEGORY + VALID_CATEGORY_TEA;

    public static final String INVALID_DRINKNAME_DESC = " " + PREFIX_DRINKNAME + ""; // empty name not allowed
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "-5.0"; // negative price not allowed
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + ""; // empty category not allowed

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_ALEX = new EditStaffDescriptorBuilder().withName(VALID_NAME_ALEX)
                .withPhone(VALID_PHONE_ALEX).withEmail(VALID_EMAIL_ALEX).withAddress(VALID_ADDRESS_ALEX)
                .withTags(VALID_TAG_MANAGER).withStaffId(VALID_STAFF_ID_ALEX).withRole(VALID_ROLE_ALEX)
                .withShiftTiming(VALID_SHIFT_ALEX).withHoursWorked(VALID_HOURS_ALEX)
                .withPerformanceRating(VALID_RATING_ALEX).build();
        DESC_BEN = new EditStaffDescriptorBuilder().withName(VALID_NAME_BEN)
                .withPhone(VALID_PHONE_BEN).withEmail(VALID_EMAIL_BEN).withAddress(VALID_ADDRESS_BEN)
                .withTags(VALID_TAG_MANAGER).withStaffId(VALID_STAFF_ID_BEN).withRole(VALID_ROLE_BEN)
                .withShiftTiming(VALID_SHIFT_BEN).withHoursWorked(VALID_HOURS_BEN)
                .withPerformanceRating(VALID_RATING_BEN).build();
        DESC_JAMES = new EditCustomerDescriptorBuilder().withName(VALID_NAME_JAMES)
            .withPhone(VALID_PHONE_JAMES).withEmail(VALID_EMAIL_JAMES).withAddress(VALID_ADDRESS_JAMES)
            .withTags(VALID_TAG_STUDENT).withCustomerId(VALID_CUSTOMER_ID_JAMES)
            .withRewardPoints(VALID_REWARD_POINTS_JAMES).withVisitCount(VALID_VISIT_COUNT_JAMES)
            .withFavouriteDrink(VALID_FAVOURITE_DRINK_JAMES).withTotalSpent(VALID_TOTAL_SPENT_JAMES).build();
        DESC_OLIVIA = new EditCustomerDescriptorBuilder().withName(VALID_NAME_OLIVIA)
            .withPhone(VALID_PHONE_OLIVIA).withEmail(VALID_EMAIL_OLIVIA).withAddress(VALID_ADDRESS_OLIVIA)
            .withTags(VALID_TAG_NEW).withCustomerId(VALID_CUSTOMER_ID_OLIVIA)
            .withRewardPoints(VALID_REWARD_POINTS_OLIVIA).withVisitCount(VALID_VISIT_COUNT_OLIVIA)
            .withFavouriteDrink(VALID_FAVOURITE_DRINK_OLIVIA).withTotalSpent(VALID_TOTAL_SPENT_OLIVIA).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the staff at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStaffAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStaffList().size());

        Person staff = model.getFilteredStaffList().get(targetIndex.getZeroBased());
        final String[] splitName = staff.getName().fullName.split("\\s+");
        model.updateFilteredStaffList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStaffList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the customer at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCustomerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCustomerList().size());

        Person customer = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        final String[] splitName = customer.getName().fullName.split("\\s+");
        model.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCustomerList().size());
    }
}
