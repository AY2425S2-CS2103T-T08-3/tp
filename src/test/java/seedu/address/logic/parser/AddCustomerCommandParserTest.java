package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_ID_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_ID_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.FAVOURITE_DRINK_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.FAVOURITE_DRINK_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.REWARD_POINTS_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.REWARD_POINTS_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.TOTAL_SPENT_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.TOTAL_SPENT_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAVOURITE_DRINK_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REWARD_POINTS_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_SPENT_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISIT_COUNT_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VISIT_COUNT_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VISIT_COUNT_DESC_OLIVIA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_DRINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.tag.Tag;

public class AddCustomerCommandParserTest {
    private AddCustomerCommandParser parser = new AddCustomerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES + FAVOURITE_DRINK_DESC_JAMES
                + TOTAL_SPENT_DESC_JAMES + TAG_DESC_STUDENT;

        AddCustomerCommand expectedCommand = new AddCustomerCommand(
                new Customer(new Name(VALID_NAME_JAMES),
                        new Phone(VALID_PHONE_JAMES),
                        new Email(VALID_EMAIL_JAMES),
                        new Address(VALID_ADDRESS_JAMES),
                        new Remark(""),
                        Set.of(new Tag(VALID_TAG_STUDENT)),
                        new CustomerId(VALID_CUSTOMER_ID_JAMES),
                        new RewardPoints(VALID_REWARD_POINTS_JAMES),
                        new VisitCount(VALID_VISIT_COUNT_JAMES),
                        new Drink(VALID_FAVOURITE_DRINK_JAMES),
                        new TotalSpent(VALID_TOTAL_SPENT_JAMES)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedCustomerString = NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES
                + ADDRESS_DESC_JAMES + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES + TAG_DESC_STUDENT;

        // multiple names
        assertParseFailure(parser, NAME_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple reward points
        assertParseFailure(parser, REWARD_POINTS_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REWARD_POINTS));

        // multiple visit counts
        assertParseFailure(parser, VISIT_COUNT_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VISIT_COUNT));

        // multiple favourite drinks
        assertParseFailure(parser, FAVOURITE_DRINK_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FAVOURITE_DRINK));

        // multiple total spent values
        assertParseFailure(parser, TOTAL_SPENT_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TOTAL_SPENT));

        // multiple ratings
        assertParseFailure(parser, CUSTOMER_ID_DESC_OLIVIA + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER_ID));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedCustomerString + PHONE_DESC_OLIVIA + EMAIL_DESC_OLIVIA
                        + NAME_DESC_OLIVIA + ADDRESS_DESC_OLIVIA + CUSTOMER_ID_DESC_OLIVIA + REWARD_POINTS_DESC_OLIVIA
                        + VISIT_COUNT_DESC_OLIVIA + FAVOURITE_DRINK_DESC_OLIVIA + TOTAL_SPENT_DESC_OLIVIA
                        + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_CUSTOMER_ID,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT, PREFIX_FAVOURITE_DRINK,
                        PREFIX_TOTAL_SPENT));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE);

        // missing customer id prefix
        assertParseFailure(parser, NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                        + VALID_ADDRESS_JAMES + REWARD_POINTS_DESC_JAMES + VALID_VISIT_COUNT_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_JAMES + VALID_PHONE_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_JAMES + PHONE_DESC_JAMES + VALID_EMAIL_JAMES + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES, expectedMessage);

        // missing reward points prefix
        assertParseFailure(parser, NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + VALID_REWARD_POINTS_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES, expectedMessage);

        // missing visit count prefix
        assertParseFailure(parser, NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VALID_VISIT_COUNT_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_JAMES + INVALID_PHONE_DESC + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_JAMES + PHONE_DESC_JAMES + INVALID_EMAIL_DESC + ADDRESS_DESC_JAMES
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + INVALID_ADDRESS_DESC
                        + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                        + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES,
                Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + ADDRESS_DESC_JAMES
                + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_JAMES + PHONE_DESC_JAMES + EMAIL_DESC_JAMES
                + ADDRESS_DESC_JAMES + CUSTOMER_ID_DESC_JAMES + REWARD_POINTS_DESC_JAMES + VISIT_COUNT_DESC_JAMES
                + FAVOURITE_DRINK_DESC_JAMES + TOTAL_SPENT_DESC_JAMES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validFastAdd_success() {
        String userInput = "C1230:John:91234567"; // Simulating fast-add format

        // Define the expected AddCustomerCommand with the correct default values
        AddCustomerCommand expectedCommand = new AddCustomerCommand(
                new Customer(
                        new Name("John"),
                        new Phone("91234567"),
                        new Email("default@gmail.com"),
                        new Address("empty"),
                        new Remark(""),
                        Set.of(),
                        new CustomerId("C1230"),
                        new RewardPoints("0"),
                        new VisitCount("1"),
                        new Drink("unknown"),
                        new TotalSpent("10")
                )
        );

        // Assert that the parsed command matches the expected command
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
