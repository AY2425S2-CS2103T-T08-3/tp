package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS_WORKED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERFORMANCE_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFT_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAFF_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

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

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

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
    public static final int VALID_HOURS_ALEX = 40;
    public static final int VALID_HOURS_BEN = 35;
    public static final double VALID_RATING_ALEX = 4.5;
    public static final double VALID_RATING_BEN = 4.2;

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


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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

}
