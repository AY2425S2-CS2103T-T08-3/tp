package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDrinks.LATTE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.drink.Category;
import seedu.address.model.drink.DrinkName;
import seedu.address.model.drink.Price;

public class JsonAdaptedDrinkTest {
    private static final DrinkName VALID_NAME = LATTE.getDrinkName();
    private static final Price VALID_PRICE = LATTE.getPrice();
    private static final Category VALID_CATEGORY = LATTE.getCategory();

    @Test
    public void toModelType_validDrinkDetails_returnsDrink() throws Exception {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(LATTE);
        assertEquals(LATTE, drink.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                new DrinkName(""), VALID_PRICE, VALID_CATEGORY);
        String expectedMessage = "Drink name cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                null, VALID_PRICE, VALID_CATEGORY);
        String expectedMessage = "Drink name cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                VALID_NAME, new Price(0), VALID_CATEGORY);
        String expectedMessage = "Drink price must be a positive number";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                VALID_NAME, VALID_PRICE, new Category(""));
        String expectedMessage = "Drink category cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedDrink drink = new JsonAdaptedDrink(
                VALID_NAME, VALID_PRICE, null);
        String expectedMessage = "Drink category cannot be blank";
        assertThrows(IllegalValueException.class, expectedMessage, drink::toModelType);
    }
}
