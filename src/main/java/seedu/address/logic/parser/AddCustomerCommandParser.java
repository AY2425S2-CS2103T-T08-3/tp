package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_DRINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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

/**
 * Parses input arguments and creates a new AddCustomerCommand object
 */
public class AddCustomerCommandParser implements Parser<AddCustomerCommand> {
    private static final String DEFAULT_EMAIL = "default@gmail.com";
    private static final String DEFAULT_ADDRESS = "empty";
    private static final String DEFAULT_REMARK = "";
    private static final String DEFAULT_VISIT_COUNT = "1";
    private static final String DEFAULT_TOTAL_SPENT = "10";
    private static final String DEFAULT_REWARD_POINTS = "0";
    private static final String DEFAULT_FAVOURITE_DRINK = "unknown";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCustomerCommand
     * and returns an AddCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AddCustomerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Fast add format: <customerId>:<name>:<phone>
        if (trimmedArgs.matches("^[^\\s:]+:[^\\s:]+:[^\\s:]+$")) {
            String[] customerInfo = trimmedArgs.split(":");

            if (customerInfo.length != 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddCustomerCommand.MESSAGE_USAGE));
            }

            CustomerId customerId = new CustomerId(customerInfo[0]);
            Name name = ParserUtil.parseName(customerInfo[1]);
            Phone phone = ParserUtil.parsePhone(customerInfo[2]);

            Email email = new Email(DEFAULT_EMAIL);
            Address address = new Address(DEFAULT_ADDRESS);
            Remark remark = new Remark(DEFAULT_REMARK);
            Set<Tag> tagList = Set.of();

            RewardPoints rewardPoints = new RewardPoints(DEFAULT_REWARD_POINTS);
            VisitCount visitCount = new VisitCount(DEFAULT_VISIT_COUNT);
            Drink favouriteDrink = new Drink(DEFAULT_FAVOURITE_DRINK);
            TotalSpent totalSpent = new TotalSpent(DEFAULT_TOTAL_SPENT);

            Customer customer = new Customer(name, phone, email, address, remark, tagList,
                    customerId, rewardPoints, visitCount, favouriteDrink, totalSpent);

            return new AddCustomerCommand(customer);
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_CUSTOMER_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT, PREFIX_FAVOURITE_DRINK, PREFIX_TOTAL_SPENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_CUSTOMER_ID, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
            PREFIX_EMAIL, PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT, PREFIX_FAVOURITE_DRINK, PREFIX_TOTAL_SPENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CUSTOMER_ID, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT, PREFIX_FAVOURITE_DRINK, PREFIX_TOTAL_SPENT);

        CustomerId customerId = new CustomerId(argMultimap.getValue(PREFIX_CUSTOMER_ID).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Remark remark = new Remark(""); // no remarks at creation
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // Wrap the parsed primitives into their domain-specific types
        RewardPoints rewardPoints = new RewardPoints(argMultimap.getValue(PREFIX_REWARD_POINTS).get());
        VisitCount visitCount = new VisitCount(argMultimap.getValue(PREFIX_VISIT_COUNT).get());
        Drink favouriteDrink = new Drink(argMultimap.getValue(PREFIX_FAVOURITE_DRINK).get());
        TotalSpent totalSpent = new TotalSpent(argMultimap.getValue(PREFIX_TOTAL_SPENT).get());

        Customer customer = new Customer(name, phone, email, address, remark, tagList,
                customerId, rewardPoints, visitCount, favouriteDrink, totalSpent);

        return new AddCustomerCommand(customer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
