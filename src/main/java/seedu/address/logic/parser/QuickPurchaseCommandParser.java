package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.QuickPurchaseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new QuickPurchaseCommand object
 */
public class QuickPurchaseCommandParser implements Parser<QuickPurchaseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the QuickPurchaseCommand
     * and returns a QuickPurchaseCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public QuickPurchaseCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (!trimmedArgs.matches("^[0-9]+:[A-Za-z ]+?(:r)?$")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split(":");

        // Customer index
        Index index = ParserUtil.parseIndex(parts[0]);

        if (parts.length < 2 || parts[1].isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuickPurchaseCommand.MESSAGE_USAGE));
        }

        // Drink name
        String drinkName = parts[1];

        // Redeem flag
        boolean isRedemption = parts.length == 3 && parts[2].equalsIgnoreCase("r");
        return new QuickPurchaseCommand(index, drinkName, isRedemption);
    }
}
