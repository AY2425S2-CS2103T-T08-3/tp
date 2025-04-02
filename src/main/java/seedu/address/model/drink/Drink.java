package seedu.address.model.drink;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Drink in the café management system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Drink {

    // Static variables
    public static final String MESSAGE_CONSTRAINTS = "Drinks can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = ".*";

    // Identity fields
    public final String value;
    private final DrinkName drinkName;
    private final Price price;
    private final Category category;

    // Optional fields - stored as transient to not affect equals/hashCode
    private transient String description;
    private transient int stock;

    /**
     * Basic constructor with required fields only.
     * Every field must be present and not null.
     *
     * @param drinkName The name of the drink.
     * @param price The price of the drink.
     * @param category The category of the drink.
     */
    public Drink(DrinkName drinkName, Price price, Category category) {
        requireAllNonNull(drinkName, price, category);
        this.drinkName = drinkName;
        this.price = price;
        this.category = category;

        // Default values for optional fields
        this.description = "";
        this.stock = 0;

        this.value = drinkName.toString();
    }

    /**
     * Constructor with all fields including optional ones.
     *
     * @param drinkName The name of the drink.
     * @param price The price of the drink.
     * @param category The category of the drink.
     * @param description The description of the drink.
     * @param stock The stock quantity of the drink.
     */
    public Drink(DrinkName drinkName, Price price, Category category, String description, int stock) {
        requireAllNonNull(drinkName, price, category);
        this.drinkName = drinkName;
        this.price = price;
        this.category = category;
        this.description = description != null ? description : "";
        this.stock = stock;

        this.value = drinkName.toString();
    }

    /**
     * Constructor with required fields as strings and double.
     *
     * @param drinkName The name of the drink.
     * @param price The price of the drink.
     * @param category The category of the drink.
     */
    public Drink(String drinkName, double price, String category) {
        this(new DrinkName(drinkName), new Price(price), new Category(category));
    }

    /**
     * // TODO: ensure that each drinkName is mapped to a list of accepted drink classes.
     * Constructor with required fields as strings.
     *
     * @param drinkName The name of the drink.
     */
    public Drink(String drinkName) {
        this(drinkName, 1, "Unknown");
    }

    /**
     * Constructor with all fields including optional ones, with required fields as strings and double.
     *
     * @param drinkName The name of the drink.
     * @param price The price of the drink.
     * @param category The category of the drink.
     * @param description The description of the drink.
     * @param stock The stock quantity of the drink.
     */
    public Drink(String drinkName, double price, String category, String description, int stock) {
        this(new DrinkName(drinkName), new Price(price), new Category(category), description, stock);
    }

    public DrinkName getDrinkName() {
        return drinkName;
    }

    public Price getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public int getStock() {
        return stock;
    }

    /**
     * Sets the description without affecting equals/hashCode.
     * Note: This does not affect the immutability of the core fields.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the stock without affecting equals/hashCode.
     * Note: This does not affect the immutability of the core fields.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns true if both drinks have the same name.
     * This defines a weaker notion of equality between two drinks.
     */
    public boolean isSameDrink(Drink otherDrink) {
        if (otherDrink == this) {
            return true;
        }

        return otherDrink != null
                && otherDrink.getDrinkName().toString().equalsIgnoreCase(getDrinkName().toString());
    }

    /**
     * Returns true if both drinks have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Drink)) {
            return false;
        }

        Drink otherDrink = (Drink) other;
        return drinkName.getDrinkName().equalsIgnoreCase(otherDrink.drinkName.getDrinkName())
                && Double.compare(price.getPrice(), otherDrink.price.getPrice()) == 0
                && category.equals(otherDrink.category);
    }

    /**
     * Returns true if a given string is a valid drink name.
     */
    public static boolean isValidDrink(String test) {
        return test != null && !test.trim().isEmpty() && test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drinkName, price, category);
    }

    @Override
    public String toString() {
        return getDrinkName().toString() + " $" + getPrice().toString() + " Category: " + getCategory().toString();
    }
}
