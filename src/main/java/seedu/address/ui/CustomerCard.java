package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Customer;

/**
 * An UI component that displays information of a {@code Customer}.
 */
public class CustomerCard extends UiPart<Region> {

    private static final String FXML = "CustomerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Customer customer;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label customerName;
    @FXML
    private Label customerId;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label visitCount;
    @FXML
    private Label totalSpent;
    @FXML
    private Label favouriteDrink;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code CustomerCard} with the given {@code Customer} and index to display.
     */
    public CustomerCard(Customer customer, int displayedIndex) {
        super(FXML);
        this.customer = customer;
        id.setText(displayedIndex + ". ");
        customerName.setText(customer.getName().fullName);
        customerId.setText("ID: " + customer.getCustomerId());
        phone.setText("Phone: " + customer.getPhone().value);
        email.setText("Email: " + customer.getEmail().value);
        visitCount.setText("Visits: " + customer.getVisitCount());
        totalSpent.setText("Total: $" + String.format("%.2f", Double.parseDouble(customer.getTotalSpent().value)));
        favouriteDrink.setText("Favourite Drink: " + customer.getFavouriteDrink().value);

        // Set tags
        customer.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add("tag");
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerCard)) {
            return false;
        }

        // state check
        CustomerCard card = (CustomerCard) other;
        return id.getText().equals(card.id.getText())
                && customer.equals(card.customer);
    }
}
