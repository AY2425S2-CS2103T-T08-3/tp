package seedu.address.model.drink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class DrinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Drink(null, 5.0, "Coffee"));
        assertThrows(NullPointerException.class, () -> new Drink("Latte", 5.0, null));
    }

    @Test
    public void isSameDrink() {
        Drink latte = new Drink("Latte", 4.50, "Coffee");

        // same object -> returns true
        assertTrue(latte.isSameDrink(latte));

        // null -> returns false
        assertFalse(latte.isSameDrink(null));

        // same name, different price and category -> returns true
        Drink editedLatte = new Drink("Latte", 5.00, "Specialty");
        assertTrue(latte.isSameDrink(editedLatte));

        // different name -> returns false
        editedLatte = new Drink("Espresso", 4.50, "Coffee");
        assertFalse(latte.isSameDrink(editedLatte));

        // different case for name -> returns true
        editedLatte = new Drink("latte", 4.50, "Coffee");
        assertTrue(latte.isSameDrink(editedLatte));
    }

    @Test
    public void equals() {
        Drink latte = new Drink("Latte", 4.50, "Coffee");

        // same values -> returns true
        Drink latteWithSameValues = new Drink("Latte", 4.50, "Coffee");
        assertTrue(latte.equals(latteWithSameValues));

        // same object -> returns true
        assertTrue(latte.equals(latte));

        // null -> returns false
        assertFalse(latte.equals(null));

        // different type -> returns false
        assertFalse(latte.equals(5));

        // different name -> returns false
        Drink cappuccino = new Drink("Cappuccino", 4.50, "Coffee");
        assertFalse(latte.equals(cappuccino));

        // different price -> returns false
        Drink expensiveLatte = new Drink("Latte", 5.50, "Coffee");
        assertFalse(latte.equals(expensiveLatte));

        // different category -> returns false
        Drink specialtyLatte = new Drink("Latte", 4.50, "Specialty");
        assertFalse(latte.equals(specialtyLatte));
    }

    @Test
    public void toStringMethod() {
        Drink latte = new Drink("Latte", 4.50, "Coffee");
        String expected = "Latte Price: $4.5 Category: Coffee";
        assertEquals(expected, latte.toString());
    }

    @Test
    public void isValidDrink_validValues_returnsTrue() {
        // Drinks can be any non-blank string
        assertTrue(Drink.isValidDrink("Coffee"));
        assertTrue(Drink.isValidDrink("Apple Tea"));
        assertTrue(Drink.isValidDrink("Hot Chocolate"));
        assertTrue(Drink.isValidDrink("1234")); // Numeric values should be allowed as well
    }

    @Test
    public void constructor_validValues_createsDrink() {
        // valid drinks should not throw any exceptions
        new Drink("Nut Latte", 5.0, "Coffee");
        new Drink("Apple Juice", 3.0, "Cold drinks");
        new Drink("Hot Chocolate", 2.5, "Coffee");
    }

    @Test
    public void constructor_invalidValues_throwsIllegalArgumentException() {
        // invalid drinks should throw an IllegalArgumentException
        try {
            new Drink("", 5.0, "Food"); // Empty string should throw an exception
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(Drink.MESSAGE_CONSTRAINTS));
        }
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Drink drink1 = new Drink("latte", 5.0, "Coffee");
        Drink drink2 = new Drink("latte", 5.0, "Coffee");
        assertTrue(drink1.equals(drink2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Drink drink1 = new Drink("Pizza", 5.0, "Food");
        Drink drink2 = new Drink("Apple", 3.0, "Fruit");
        assertFalse(drink1.equals(drink2));
    }

    @Test
    public void toString_testValue() {
        Drink drink = new Drink("Pizza", 5.0, "Food");
        assertTrue(drink.toString().equals("Pizza $5.0 Category: Food"));
    }
}
