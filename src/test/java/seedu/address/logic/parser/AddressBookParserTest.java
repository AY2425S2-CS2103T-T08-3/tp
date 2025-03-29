package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HoursAddCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.descriptors.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.util.PersonBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + remark);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommand_hoursAdd() throws Exception {
        // Correct format: hoursadd <index> <hours>
        HoursAddCommand command = (HoursAddCommand) parser.parseCommand(
                HoursAddCommand.COMMAND_WORD + " " + PREFIX_INDEX + "1 " + PREFIX_HOURS + "8");
        assertEquals(new HoursAddCommand(INDEX_FIRST_PERSON, 8), command);

        // Another valid test case with different hours
        command = (HoursAddCommand) parser.parseCommand(
                HoursAddCommand.COMMAND_WORD + " " + PREFIX_INDEX + "1 " + PREFIX_HOURS + "10");
        assertEquals(new HoursAddCommand(INDEX_FIRST_PERSON, 10), command);
    }

    @Test
    public void parseCommand_hoursAddInvalidInputThrowsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HoursAddCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(HoursAddCommand.COMMAND_WORD + " 1 " + PREFIX_HOURS + "-5"));
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        HoursAddCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(HoursAddCommand.COMMAND_WORD + " abc " + PREFIX_HOURS + "8"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    // New tests for the fuzzy matching functionality

    @Test
    public void parseCommand_typoWithCloseLevenshteinDistance_suggestsCorrectCommand() {
        // Test close typos that should be caught by Levenshtein distance (≤ 2)
        try {
            parser.parseCommand("ad");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'add'?"));
        }

        try {
            parser.parseCommand("delte");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'delete'?"));
        }

        try {
            parser.parseCommand("lisr");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'list'?"));
        }

        try {
            parser.parseCommand("cler");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'clear'?"));
        }
    }

    @Test
    public void parseCommand_typoWithTokenSetRatioMatch_suggestsCorrectCommand() {
        // Test typos that should be caught by token set ratio (≥ 80%)
        try {
            parser.parseCommand("hlep");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'help'?"));
        }

        try {
            parser.parseCommand("edti");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'edit'?"));
        }

        try {
            parser.parseCommand("fnid");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertTrue(e.getMessage().contains("Did you mean 'find'?"));
        }
    }

    @Test
    public void parseCommand_commandWithNoCloseMatch_doesNotSuggest() {
        // Test commands that are too different to suggest alternatives
        try {
            parser.parseCommand("xyz");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }

        try {
            parser.parseCommand("commandnotexist");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }

        try {
            parser.parseCommand("randomtext");
            assertTrue(false, "Expected ParseException was not thrown");
        } catch (ParseException e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }
    }
}
