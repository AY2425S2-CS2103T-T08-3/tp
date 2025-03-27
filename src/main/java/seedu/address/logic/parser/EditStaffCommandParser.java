package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditStaffCommand;
import seedu.address.logic.commands.EditStaffCommand.EditStaffDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditStaffCommand object
 */
public class EditStaffCommandParser implements Parser<EditStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditStaffCommand
     * and returns an EditStaffCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditStaffCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_STAFF_ID, PREFIX_ROLE, PREFIX_SHIFT_TIMING, PREFIX_HOURS_WORKED, PREFIX_PERFORMANCE_RATING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStaffCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_STAFF_ID, PREFIX_ROLE, PREFIX_SHIFT_TIMING, PREFIX_HOURS_WORKED, PREFIX_PERFORMANCE_RATING);

        EditStaffDescriptor editStaffDescriptor = new EditStaffDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStaffDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editStaffDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editStaffDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editStaffDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_STAFF_ID).isPresent()) {
            editStaffDescriptor.setPersonId(ParserUtil.parseStaffId(argMultimap.getValue(PREFIX_STAFF_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editStaffDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }
        if (argMultimap.getValue(PREFIX_SHIFT_TIMING).isPresent()) {
            editStaffDescriptor.setShiftTiming(
                ParserUtil.parseShiftTiming(argMultimap.getValue(PREFIX_SHIFT_TIMING).get())
            );
        }
        if (argMultimap.getValue(PREFIX_HOURS_WORKED).isPresent()) {
            editStaffDescriptor.setHoursWorked(
                ParserUtil.parseHoursWorked(argMultimap.getValue(PREFIX_HOURS_WORKED).get())
            );
        }
        if (argMultimap.getValue(PREFIX_PERFORMANCE_RATING).isPresent()) {
            editStaffDescriptor.setPerformanceRating(
                ParserUtil.parsePerformanceRating(argMultimap.getValue(PREFIX_PERFORMANCE_RATING).get())
            );
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editStaffDescriptor::setTags);

        if (!editStaffDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditStaffCommand.MESSAGE_NOT_EDITED);
        }

        return new EditStaffCommand(index, editStaffDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
