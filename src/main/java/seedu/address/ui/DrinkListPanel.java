package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.drink.Drink;

/**
 * Panel containing the list of drinks.
 */
public class DrinkListPanel extends UiPart<Region> {
    private static final String FXML = "DrinkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DrinkListPanel.class);

    @FXML
    private ListView<Drink> drinkListView;

    // Handler for drink selection events
    private Consumer<Drink> selectionHandler;

    /**
     * Creates a {@code DrinkListPanel} with the given {@code ObservableList}.
     */
    public DrinkListPanel(ObservableList<Drink> drinkList) {
        super(FXML);
        drinkListView.setItems(drinkList != null ? drinkList : FXCollections.observableArrayList());
        drinkListView.setCellFactory(listView -> new DrinkListViewCell());

        // Add listener for drink selection
        drinkListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && selectionHandler != null) {
                logger.fine("Selection changed to: " + newValue.getDrinkName());
                selectionHandler.accept(newValue);
            }
        });
    }

    /**
     * Sets the handler for drink selection events.
     *
     * @param handler the function to call when a drink is selected
     */
    public void setDrinkSelectionHandler(Consumer<Drink> handler) {
        this.selectionHandler = handler;
    }

    /**
     * Selects the specified drink in the list view, if present.
     *
     * @param drink The drink to select
     * @return true if selection was successful, false if drink not found
     */
    public boolean selectDrink(Drink drink) {
        if (drink == null) {
            return false;
        }

        // Find the index of the drink in the list
        for (int i = 0; i < drinkListView.getItems().size(); i++) {
            if (drinkListView.getItems().get(i).equals(drink)) {
                drinkListView.getSelectionModel().select(i);
                drinkListView.scrollTo(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Drink} using a {@code DrinkCard}.
     */
    class DrinkListViewCell extends ListCell<Drink> {
        @Override
        protected void updateItem(Drink drink, boolean empty) {
            super.updateItem(drink, empty);

            if (empty || drink == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DrinkCard(drink, getIndex() + 1).getRoot());
            }
        }
    }
}
