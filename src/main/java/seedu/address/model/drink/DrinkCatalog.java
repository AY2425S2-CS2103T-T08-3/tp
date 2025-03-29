package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the drink-catalog level.
 * Duplicates are not allowed (by .isSameDrink comparison).
 */
public class DrinkCatalog implements ReadOnlyDrinkCatalog {

    private final UniqueDrinkList drinks;

    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication between constructors.
     * Note that non-static init blocks are not recommended to use.
     */
    {
        drinks = new UniqueDrinkList();
    }

    public DrinkCatalog() {}

    /**
     * Creates a DrinkCatalog using the Drinks in {@code toBeCopied}.
     */
    public DrinkCatalog(ReadOnlyDrinkCatalog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the drink list with {@code drinks}.
     * {@code drinks} must not contain duplicate drinks.
     */
    public void setDrinks(List<Drink> drinks) {
        this.drinks.setDrinks(drinks);
    }

    /**
     * Resets the existing data of this {@code DrinkCatalog} with {@code newData}.
     */
    public void resetData(ReadOnlyDrinkCatalog newData) {
        requireNonNull(newData);
        setDrinks(newData.getDrinkList());
    }

    //// drink-level operations

    /**
     * Returns true if a drink with the same identity as {@code drink} exists in the drink catalog.
     */
    public boolean hasDrink(Drink drink) {
        requireNonNull(drink);
        return drinks.contains(drink);
    }

    /**
     * Adds a drink to the drink catalog.
     * The drink must not already exist in the drink catalog.
     */
    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    /**
     * Replaces the given drink {@code target} in the list with {@code editedDrink}.
     * {@code target} must exist in the drink catalog.
     * The identity of {@code editedDrink} must not be the same as another existing drink in the drink catalog.
     */
    public void setDrink(Drink target, Drink editedDrink) {
        requireNonNull(editedDrink);
        drinks.setDrink(target, editedDrink);
    }

    /**
     * Removes {@code key} from this {@code DrinkCatalog}.
     * {@code key} must exist in the drink catalog.
     */
    public void removeDrink(Drink key) {
        drinks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return drinks.asUnmodifiableObservableList().size() + " drinks";
    }

    @Override
    public ObservableList<Drink> getDrinkList() {
        return drinks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DrinkCatalog)) {
            return false;
        }

        DrinkCatalog otherDrinkCatalog = (DrinkCatalog) other;
        return drinks.equals(otherDrinkCatalog.drinks);
    }

    @Override
    public int hashCode() {
        return drinks.hashCode();
    }
}
