package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.drink.Drink;

/**
 * An UI component that displays information of a {@code Drink}.
 */
public class DrinkCard extends UiPart<Region> {

    private static final String FXML = "DrinkListCard.fxml";

    public final Drink drink;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label drinkName;
    @FXML
    private Label price;
    @FXML
    private Label category;

    /**
     * Creates a {@code DrinkCard} with the given {@code Drink} and index to display.
     */
    public DrinkCard(Drink drink, int displayedIndex) {
        super(FXML);
        this.drink = drink;

        id.setText(displayedIndex + ". ");
        drinkName.setText(drink.getDrinkName().toString());
        price.setText("$" + String.format("%.2f", drink.getPrice()));
        category.setText("Category: " + drink.getCategory());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DrinkCard)) {
            return false;
        }

        // state check
        DrinkCard card = (DrinkCard) other;
        return id.getText().equals(card.id.getText())
                && drink.equals(card.drink);
    }
}
