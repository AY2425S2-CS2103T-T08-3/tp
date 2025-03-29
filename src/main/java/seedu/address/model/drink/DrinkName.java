package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Drink's name.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}.
 */
public class DrinkName {

    public static final String MESSAGE_CONSTRAINTS = "Drink names should not be blank";

    public final String drinkName;

    /**
     * Constructs a {@code DrinkName}.
     *
     * @param drinkName A valid name.
     */
    public DrinkName(String drinkName) {
        requireNonNull(drinkName);
        if (!isValidName(drinkName)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.drinkName = drinkName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test != null && !test.trim().isEmpty(); // Ensure the name is not null, blank, or just spaces
    }

    public String getDrinkName() {
        return drinkName;
    }

    @Override
    public String toString() {
        return drinkName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DrinkName
                && drinkName.equalsIgnoreCase(((DrinkName) other).drinkName)); // Case-insensitive comparison
    }

    @Override
    public int hashCode() {
        return drinkName.hashCode();
    }
}
