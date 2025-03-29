package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.drink.exceptions.DrinkNotFoundException;
import seedu.address.model.drink.exceptions.DuplicateDrinkException;

/**
 * A list of drinks that enforces uniqueness between its elements and does not allow nulls.
 * A drink is considered unique by comparing using {@code Drink#isSameDrink(Drink)}.
 * Adding and updating of drinks uses {@code Drink#isSameDrink(Drink)} for equality to ensure
 * that the drink being added or updated is unique in terms of identity in the list.
 * However, removal of a drink uses {@code Drink#equals(Object)} to ensure that the
 * drink with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Drink#isSameDrink(Drink)
 */
public class UniqueDrinkList implements Iterable<Drink> {

    private static final ObservableList<Drink> internalList = FXCollections.observableArrayList();
    private final ObservableList<Drink> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Loads the provided list of drinks into the internal list.
     * This method ensures that the internal list is populated only if it is currently empty.
     *
     * @param drinks The list of drinks to be loaded into the internal list.
     *               Must not be null and must not contain duplicate drinks.
     */
    public static void loadDrinks(List<Drink> drinks) {
        if (internalList.isEmpty()) {
            internalList.addAll(drinks);
        }
    }

    /**
     * Returns true if the list contains an equivalent drink as the given argument.
     */
    public boolean contains(Drink toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDrink);
    }

    /**
     * Adds a drink to the list.
     * The drink must not already exist in the list.
     */
    public void add(Drink toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDrinkException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the drink {@code target} in the list with {@code editedDrink}.
     * {@code target} must exist in the list.
     * The drink identity of {@code editedDrink} must not be the same as another existing drink in the list.
     */
    public void setDrink(Drink target, Drink editedDrink) {
        requireAllNonNull(target, editedDrink);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DrinkNotFoundException();
        }

        if (!target.isSameDrink(editedDrink) && contains(editedDrink)) {
            throw new DuplicateDrinkException();
        }

        internalList.set(index, editedDrink);
    }

    /**
     * Removes the equivalent drink from the list.
     * The drink must exist in the list.
     */
    public void remove(Drink toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DrinkNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code drinks}.
     * {@code drinks} must not contain duplicate drinks.
     */
    public void setDrinks(List<Drink> drinks) {
        requireAllNonNull(drinks);
        if (!drinksAreUnique(drinks)) {
            throw new DuplicateDrinkException();
        }
        internalList.setAll(drinks);
    }

    /**
     * Replaces the current list with another {@code UniqueDrinkList}.
     */
    public void setDrinks(UniqueDrinkList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Drink> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Finds and returns a drink object with the given name.
     * If not found, throws DrinkNotFoundException.
     */
    public static Drink findDrinkByName(String drinkName) {
        requireNonNull(drinkName);

        return internalList.stream()
                .filter(drink -> drink.getDrinkName().toString().equalsIgnoreCase(drinkName))
                .findFirst()
                .orElseThrow(() -> new DrinkNotFoundException());
    }

    @Override
    public Iterator<Drink> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueDrinkList)) {
            return false;
        }

        UniqueDrinkList otherUniqueDrinkList = (UniqueDrinkList) other;
        return internalList.equals(otherUniqueDrinkList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code drinks} contains only unique drinks.
     */
    private boolean drinksAreUnique(List<Drink> drinks) {
        for (int i = 0; i < drinks.size() - 1; i++) {
            for (int j = i + 1; j < drinks.size(); j++) {
                if (drinks.get(i).isSameDrink(drinks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
