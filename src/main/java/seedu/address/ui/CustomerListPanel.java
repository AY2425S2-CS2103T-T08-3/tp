package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Customer;

/**
 * Panel containing the list of customers.
 */
public class CustomerListPanel extends UiPart<Region> {
    private static final String FXML = "CustomerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CustomerListPanel.class);

    @FXML
    private ListView<Customer> customerListView;

    // Handler for customer selection events
    private Consumer<Customer> selectionHandler;

    /**
     * Creates a {@code CustomerListPanel} with the given {@code ObservableList}.
     */
    public CustomerListPanel(ObservableList<Customer> customerList) {
        super(FXML);
        customerListView.setItems(customerList);
        customerListView.setCellFactory(listView -> new CustomerListViewCell());

        // Add listener for customer selection
        customerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && selectionHandler != null) {
                logger.fine("Selection changed to: " + newValue.getName().fullName);
                selectionHandler.accept(newValue);
            }
        });
    }

    /**
     * Sets the handler for customer selection events.
     *
     * @param handler the function to call when a customer is selected
     */
    public void setPersonSelectionHandler(Consumer<Customer> handler) {
        this.selectionHandler = handler;
    }

    /**
     * Selects the specified customer in the list view, if present.
     *
     * @param customer The customer to select
     * @return true if selection was successful, false if customer not found
     */
    public boolean selectCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }

        // Find the index of the customer in the list
        for (int i = 0; i < customerListView.getItems().size(); i++) {
            if (customerListView.getItems().get(i).equals(customer)) {
                customerListView.getSelectionModel().select(i);
                customerListView.scrollTo(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Customer} using a {@code CustomerCard}.
     */
    class CustomerListViewCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);

            if (empty || customer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CustomerCard(customer, getIndex() + 1).getRoot());
            }
        }
    }
}
