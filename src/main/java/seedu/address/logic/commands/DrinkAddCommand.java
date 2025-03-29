package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;

/**
 * Adds a drink to the drink catalog.
 */
public class DrinkAddCommand extends Command {

    public static final String COMMAND_WORD = "drinkadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a drink to the drink catalog. "
            + "Parameters: "
            + PREFIX_DRINKNAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRINKNAME + "Iced Latte "
            + PREFIX_PRICE + "4.50 "
            + PREFIX_CATEGORY + "Coffee";

    public static final String MESSAGE_SUCCESS = "New drink added: %1$s";
    public static final String MESSAGE_DUPLICATE_DRINK = "This drink already exists in the drink catalog";

    private final Drink toAdd;

    /**
     * Creates a DrinkAddCommand to add the specified {@code Drink}
     */
    public DrinkAddCommand(Drink drink) {
        requireNonNull(drink);
        toAdd = drink;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDrink(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DRINK);
        }

        model.addDrink(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DrinkAddCommand)) {
            return false;
        }

        DrinkAddCommand otherDrinkAddCommand = (DrinkAddCommand) other;
        return toAdd.equals(otherDrinkAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("drink", toAdd)
                .toString();
    }
}
