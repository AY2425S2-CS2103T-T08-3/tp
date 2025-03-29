package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_LATTE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_LATTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_LATTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_LATTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DrinkAddCommand;
import seedu.address.model.drink.Drink;

public class DrinkAddCommandParserTest {
    private DrinkAddCommandParser parser = new DrinkAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Drink expectedDrink = new Drink(VALID_NAME_LATTE, VALID_PRICE_LATTE, VALID_CATEGORY_COFFEE);

        // standard case
        assertParseSuccess(parser, NAME_DESC_LATTE + PRICE_DESC_LATTE + CATEGORY_DESC_COFFEE,
                new DrinkAddCommand(expectedDrink));

        // different order of fields
        assertParseSuccess(parser, PRICE_DESC_LATTE + CATEGORY_DESC_COFFEE + NAME_DESC_LATTE,
                new DrinkAddCommand(expectedDrink));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DrinkAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_LATTE + PRICE_DESC_LATTE + CATEGORY_DESC_COFFEE,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_LATTE + VALID_PRICE_LATTE + CATEGORY_DESC_COFFEE,
                expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_LATTE + PRICE_DESC_LATTE + VALID_CATEGORY_COFFEE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_LATTE + VALID_PRICE_LATTE + VALID_CATEGORY_COFFEE,
                expectedMessage);
    }

}
