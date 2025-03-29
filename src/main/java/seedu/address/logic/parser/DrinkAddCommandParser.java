package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.stream.Stream;

import seedu.address.logic.commands.DrinkAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.Drink;

/**
 * Parses input arguments and creates a new DrinkAddCommand object
 */
public class DrinkAddCommandParser implements Parser<DrinkAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DrinkAddCommand
     * and returns a DrinkAddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DrinkAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DrinkAddCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_NAME).get();

        double price;
        try {
            price = Double.parseDouble(argMultimap.getValue(PREFIX_PRICE).get());
            if (price <= 0) {
                throw new ParseException("Price must be a positive number");
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Price must be a valid number");
        }

        String category = argMultimap.getValue(PREFIX_CATEGORY).get();

        Drink drink = new Drink(name, price, category);

        return new DrinkAddCommand(drink);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
