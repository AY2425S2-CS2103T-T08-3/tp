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
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FAVOURITE_DRINK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REWARD_POINTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TOTAL_SPENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VISIT_COUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.REWARD_POINTS_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.REWARD_POINTS_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEW;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.TOTAL_SPENT_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.TOTAL_SPENT_DESC_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAVOURITE_DRINK_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FAVOURITE_DRINK_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REWARD_POINTS_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REWARD_POINTS_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_SPENT_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_SPENT_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISIT_COUNT_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VISIT_COUNT_OLIVIA;
import static seedu.address.logic.commands.CommandTestUtil.VISIT_COUNT_DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.VISIT_COUNT_DESC_OLIVIA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCustomerCommand;
import seedu.address.logic.parser.descriptors.EditCustomerDescriptor;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Address;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCustomerDescriptorBuilder;

public class EditCustomerCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE);

    private EditCustomerCommandParser parser = new EditCustomerCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_JAMES, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCustomerCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_JAMES, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_JAMES, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        // invalid phone
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
        // invalid email
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);
        // invalid address
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
        // invalid customer ID
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_ID_DESC, CustomerId.MESSAGE_CONSTRAINTS);
        // invalid reward points
        assertParseFailure(parser, "1" + INVALID_REWARD_POINTS_DESC, RewardPoints.MESSAGE_CONSTRAINTS);
        // invalid visit count
        assertParseFailure(parser, "1" + INVALID_VISIT_COUNT_DESC, VisitCount.MESSAGE_CONSTRAINTS);
        // invalid favourite drink
        assertParseFailure(parser, "1" + INVALID_FAVOURITE_DRINK_DESC, Drink.MESSAGE_CONSTRAINTS);
        // invalid total spent
        assertParseFailure(parser, "1" + INVALID_TOTAL_SPENT_DESC, TotalSpent.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_JAMES, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code TAG_EMPTY} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(
                parser, "1" + TAG_DESC_STUDENT + TAG_DESC_NEW + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(
                parser, "1" + TAG_DESC_STUDENT + TAG_EMPTY + TAG_DESC_NEW, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(
                parser, "1" + TAG_EMPTY + TAG_DESC_STUDENT + TAG_DESC_NEW, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(
                parser,
                "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_JAMES + VALID_PHONE_JAMES,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + CUSTOMER_ID_DESC_OLIVIA + NAME_DESC_OLIVIA + PHONE_DESC_OLIVIA
            + EMAIL_DESC_OLIVIA + ADDRESS_DESC_OLIVIA + TAG_DESC_STUDENT + TAG_DESC_NEW + REWARD_POINTS_DESC_OLIVIA
            + VISIT_COUNT_DESC_OLIVIA + FAVOURITE_DRINK_DESC_OLIVIA + TOTAL_SPENT_DESC_OLIVIA;

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withCustomerId(VALID_CUSTOMER_ID_OLIVIA)
            .withName(VALID_NAME_OLIVIA).withPhone(VALID_PHONE_OLIVIA).withEmail(VALID_EMAIL_OLIVIA)
            .withAddress(VALID_ADDRESS_OLIVIA).withTags(VALID_TAG_NEW, VALID_TAG_STUDENT)
            .withRewardPoints(VALID_REWARD_POINTS_OLIVIA).withVisitCount(VALID_VISIT_COUNT_OLIVIA)
            .withFavouriteDrink(VALID_FAVOURITE_DRINK_OLIVIA).withTotalSpent(VALID_TOTAL_SPENT_OLIVIA).build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()
                + PHONE_DESC_JAMES + EMAIL_DESC_JAMES + CUSTOMER_ID_DESC_JAMES
                + TOTAL_SPENT_DESC_JAMES;

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder()
                .withPhone(VALID_PHONE_JAMES)
                .withEmail(VALID_EMAIL_JAMES)
                .withCustomerId(VALID_CUSTOMER_ID_JAMES)
                .withTotalSpent(VALID_TOTAL_SPENT_JAMES)
                .build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // name
        String userInput = targetIndex.getOneBased() + NAME_DESC_JAMES;
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withName(VALID_NAME_JAMES).build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withPhone(VALID_PHONE_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withEmail(VALID_EMAIL_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withAddress(VALID_ADDRESS_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // customer ID
        userInput = targetIndex.getOneBased() + CUSTOMER_ID_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withCustomerId(VALID_CUSTOMER_ID_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // reward points
        userInput = targetIndex.getOneBased() + REWARD_POINTS_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withRewardPoints(VALID_REWARD_POINTS_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // visit count
        userInput = targetIndex.getOneBased() + VISIT_COUNT_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withVisitCount(VALID_VISIT_COUNT_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // favourite drink
        userInput = targetIndex.getOneBased() + FAVOURITE_DRINK_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withFavouriteDrink(VALID_FAVOURITE_DRINK_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // total spent
        userInput = targetIndex.getOneBased() + TOTAL_SPENT_DESC_JAMES;
        descriptor = new EditCustomerDescriptorBuilder().withTotalSpent(VALID_TOTAL_SPENT_JAMES).build();
        expectedCommand = new EditCustomerCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetFields_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder().withTags().build();
        EditCustomerCommand expectedCommand = new EditCustomerCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
