package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_SPENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT_COUNT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCustomerCommand;
import seedu.address.logic.parser.descriptors.EditCustomerDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCustomerCommand object
 */
public class EditCustomerCommandParser implements Parser<EditCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCustomerCommand
     * and returns an EditCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCustomerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_TAG, PREFIX_CUSTOMER_ID,
                PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT, PREFIX_FAVOURITE_ITEM,
                PREFIX_TOTAL_SPENT, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCustomerCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_CUSTOMER_ID, PREFIX_REWARD_POINTS, PREFIX_VISIT_COUNT, PREFIX_FAVOURITE_ITEM, PREFIX_TOTAL_SPENT, PREFIX_REMARK);

        EditCustomerDescriptor editCustomerDescriptor = new EditCustomerDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCustomerDescriptor.setName(
                    ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editCustomerDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editCustomerDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editCustomerDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_CUSTOMER_ID).isPresent()) {
            editCustomerDescriptor.setCustomerId(
                    ParserUtil.parseCustomerId(argMultimap.getValue(PREFIX_CUSTOMER_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_REWARD_POINTS).isPresent()) {
            editCustomerDescriptor.setRewardPoints(
                ParserUtil.parseRewardPoints(argMultimap.getValue(PREFIX_REWARD_POINTS).get())
            );
        }
        if (argMultimap.getValue(PREFIX_VISIT_COUNT).isPresent()) {
            editCustomerDescriptor.setVisitCount(
                ParserUtil.parseVisitCount(argMultimap.getValue(PREFIX_VISIT_COUNT).get())
            );
        }
        if (argMultimap.getValue(PREFIX_FAVOURITE_ITEM).isPresent()) {
            editCustomerDescriptor.setFavouriteItem(
                ParserUtil.parseFavouriteItem(argMultimap.getValue(PREFIX_FAVOURITE_ITEM).get())
            );
        }
        if (argMultimap.getValue(PREFIX_TOTAL_SPENT).isPresent()) {
            editCustomerDescriptor.setTotalSpent(
                ParserUtil.parseTotalSpent(argMultimap.getValue(PREFIX_TOTAL_SPENT).get())
            );
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editCustomerDescriptor.setRemark(
                ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get())
            );
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editCustomerDescriptor::setTags);

        if (!editCustomerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCustomerCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCustomerCommand(index, editCustomerDescriptor);
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
