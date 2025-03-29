package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.drink.Category;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.DrinkName;
import seedu.address.model.drink.Price;

/**
 * Jackson-friendly version of {@link Drink}.
 */
public class JsonAdaptedDrink {

    private final DrinkName drinkName;
    private final Price price;
    private final Category category;

    /**
     * Constructs a {@code JsonAdaptedDrink} with the given drink details.
     */
    @JsonCreator
    public JsonAdaptedDrink(@JsonProperty("drinkName") DrinkName drinkName,
                           @JsonProperty("price") Price price,
                           @JsonProperty("category") Category category) {
        this.drinkName = drinkName;
        this.price = price;
        this.category = category;
    }

    /**
     * Converts a given {@code Drink} into this class for Jackson use.
     */
    public JsonAdaptedDrink(Drink source) {
        drinkName = source.getDrinkName();
        price = source.getPrice();
        category = source.getCategory();
    }

    /**
     * Converts this Jackson-friendly adapted drink object into the model's {@code Drink} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted drink.
     */
    public Drink toModelType() throws IllegalValueException {
        if (drinkName == null || drinkName.getDrinkName().trim().isEmpty()) {
            throw new IllegalValueException("Drink name cannot be blank");
        }

        if (price.getPrice() <= 0) {
            throw new IllegalValueException("Drink price must be a positive number");
        }

        if (category == null || category.getCategory().trim().isEmpty()) {
            throw new IllegalValueException("Drink category cannot be blank");
        }

        return new Drink(drinkName, price, category);
    }
}
