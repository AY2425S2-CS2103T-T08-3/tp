package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddStaffCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Staff;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddStaffCommand object
 */
public class AddStaffCommandParser implements Parser<AddStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddStaffCommand
     * and returns an AddStaffCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddStaffCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_STAFF_ID, PREFIX_ROLE, PREFIX_SHIFT_TIMING, PREFIX_HOURS_WORKED, PREFIX_PERFORMANCE_RATING);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_STAFF_ID, PREFIX_ROLE, PREFIX_SHIFT_TIMING, PREFIX_HOURS_WORKED, PREFIX_PERFORMANCE_RATING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStaffCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_STAFF_ID, PREFIX_ROLE, PREFIX_SHIFT_TIMING, PREFIX_HOURS_WORKED, PREFIX_PERFORMANCE_RATING);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Remark remark = new Remark(""); // still no remarks at creation
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        String staffId = argMultimap.getValue(PREFIX_STAFF_ID).get();
        String role = argMultimap.getValue(PREFIX_ROLE).get();
        String shiftTiming = argMultimap.getValue(PREFIX_SHIFT_TIMING).get();
        int hoursWorked = Integer.parseInt(argMultimap.getValue(PREFIX_HOURS_WORKED).get());
        double performanceRating = Double.parseDouble(argMultimap.getValue(PREFIX_PERFORMANCE_RATING).get());

        Staff staff = new Staff(name, phone, email, address, remark, tagList,
                staffId, role, shiftTiming, hoursWorked, performanceRating);

        return new AddStaffCommand(staff);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
