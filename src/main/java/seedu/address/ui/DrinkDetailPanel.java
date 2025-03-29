package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.drink.Drink;

/**
 * Panel containing the details of a drink.
 */
public class DrinkDetailPanel extends UiPart<Region> {
    private static final String FXML = "DrinkDetailView.fxml";
    private static final Logger logger = LogsCenter.getLogger(DrinkDetailPanel.class);

    @FXML
    private Label noSelectionMessage;
    @FXML
    private VBox detailsContent;
    @FXML
    private Label drinkNameHeader;
    @FXML
    private Label price;
    @FXML
    private Label category;
    @FXML
    private Label description;
    @FXML
    private Label stock;

    /**
     * Creates a {@code DrinkDetailPanel}.
     */
    public DrinkDetailPanel() {
        super(FXML);

        // Verify critical components
        boolean componentsOk = verifyComponents();
        if (!componentsOk) {
            logger.warning("Some FXML components were not properly loaded in DrinkDetailPanel");
        }

        // Hide details initially since no drink is selected
        showNoDrinkSelected();
    }

    /**
     * Verifies that critical FXML components were loaded properly.
     * @return true if all critical components exist
     */
    private boolean verifyComponents() {
        boolean allOk = true;

        if (noSelectionMessage == null) {
            logger.severe("noSelectionMessage is null");
            allOk = false;
        }

        if (detailsContent == null) {
            logger.severe("detailsContent is null");
            allOk = false;
        }

        if (drinkNameHeader == null) {
            logger.severe("drinkNameHeader is null");
            allOk = false;
        }

        // The following are less critical but still important
        if (price == null) {
            logger.severe("price is null");
            allOk = false;
        }

        if (category == null) {
            logger.severe("category is null");
            allOk = false;
        }

        return allOk;
    }

    /**
     * Shows the no drink selected message.
     */
    private void showNoDrinkSelected() {
        if (noSelectionMessage != null) {
            noSelectionMessage.setVisible(true);
        }

        if (detailsContent != null) {
            detailsContent.setVisible(false);
        }
    }

    /**
     * Updates the UI with the details of the provided drink.
     *
     * @param drink The drink to display details for
     */
    public void updateDrinkDetails(Drink drink) {
        if (drink == null) {
            showNoDrinkSelected();
            return;
        }

        logger.fine("Updating drink details for: " + drink.getDrinkName().getDrinkName());

        try {
            // Show details and hide the no selection message
            if (noSelectionMessage != null) {
                noSelectionMessage.setVisible(false);
            }

            if (detailsContent != null) {
                detailsContent.setVisible(true);
            }

            // Set the header (this is the most crucial part)
            if (drinkNameHeader != null) {
                drinkNameHeader.setText(drink.getDrinkName().getDrinkName());
            }

            // Update all fields with null safety
            safeSetText(price, String.format("$%.2f", drink.getPrice()));
            safeSetText(category, drink.getCategory().getCategory());

            // For fields that might not exist in your model yet
            safeSetText(description, "");
            safeSetText(stock, "In stock");

        } catch (Exception e) {
            logger.warning("Error updating drink details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Safely sets text to a label, handling null cases.
     *
     * @param label The label to update
     * @param text The text to set
     */
    private void safeSetText(Label label, String text) {
        if (label != null) {
            label.setText(text != null ? text : "");
        }
    }
}
