package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Customer;

/**
 * Panel containing the details of a customer.
 */
public class CustomerDetailPanel extends UiPart<Region> {
    private static final String FXML = "CustomerDetailView.fxml";
    private static final Logger logger = LogsCenter.getLogger(CustomerDetailPanel.class);

    /**
     * Interface to execute commands from the UI.
     */
    public interface CommandExecutor {
        /**
         * Executes a command and returns the result.
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    private CommandExecutor commandExecutor;

    @FXML
    private Label noSelectionMessage;
    @FXML
    private VBox detailsContent;
    @FXML
    private Label customerNameHeader;
    @FXML
    private Label customerId;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label visitCount;
    @FXML
    private Label totalSpent;
    @FXML
    private Label rewardPoints;
    @FXML
    private Label favouriteDrink;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code CustomerDetailPanel}.
     */
    public CustomerDetailPanel() {
        super(FXML);

        // Verify critical components
        boolean componentsOk = verifyComponents();
        if (!componentsOk) {
            logger.warning("Some FXML components were not properly loaded in CustomerDetailPanel");
        }

        // Hide details initially since no customer is selected
        showNoCustomerSelected();
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

        if (customerNameHeader == null) {
            logger.severe("customerNameHeader is null");
            allOk = false;
        }

        return allOk;
    }

    /**
     * Shows the no customer selected message.
     */
    private void showNoCustomerSelected() {
        if (noSelectionMessage != null) {
            noSelectionMessage.setVisible(true);
        }

        if (detailsContent != null) {
            detailsContent.setVisible(false);
        }
    }

    /**
     * Updates the UI with the details of the provided customer.
     *
     * @param customer The customer to display details for
     */
    public void updateCustomerDetails(Customer customer) {
        if (customer == null) {
            showNoCustomerSelected();
            return;
        }

        logger.fine("Updating customer details for: " + customer.getName().fullName);

        try {
            // Show details and hide the no selection message
            if (noSelectionMessage != null) {
                noSelectionMessage.setVisible(false);
            }

            if (detailsContent != null) {
                detailsContent.setVisible(true);
            }

            // Set the header (this is the most crucial part)
            if (customerNameHeader != null) {
                customerNameHeader.setText(customer.getName().fullName);
            }

            // Update all fields with null safety
            safeSetText(customerId, customer.getCustomerId().toString());
            safeSetText(phone, customer.getPhone() != null ? customer.getPhone().value : "");
            safeSetText(email, customer.getEmail() != null ? customer.getEmail().value : "");
            safeSetText(address, customer.getAddress() != null ? customer.getAddress().value : "");

            // For fields that might be specific to Customer subclass
            try {
                safeSetText(visitCount, String.valueOf(customer.getVisitCount()));
            } catch (Exception e) {
                safeSetText(visitCount, "0");
            }

            try {
                safeSetText(totalSpent, String.valueOf(customer.getTotalSpent()));
            } catch (Exception e) {
                safeSetText(totalSpent, "$0.00");
            }

            try {
                safeSetText(rewardPoints, String.valueOf(customer.getRewardPoints()));
            } catch (Exception e) {
                safeSetText(rewardPoints, "0");
            }

            try {
                safeSetText(favouriteDrink, customer.getFavouriteDrink().toString());
            } catch (Exception e) {
                safeSetText(favouriteDrink, "None");
            }

            try {
                safeSetText(remark, customer.getRemark() != null && customer.getRemark().value != null
                        ? customer.getRemark().value : "");
            } catch (Exception e) {
                safeSetText(remark, "No notes");
            }

            // Clear existing tags and add new ones
            if (tags != null) {
                tags.getChildren().clear();

                if (customer.getTags() != null) {
                    customer.getTags().forEach(tag -> {
                        Label tagLabel = new Label(tag.tagName);
                        tagLabel.getStyleClass().add("tag");
                        tags.getChildren().add(tagLabel);
                    });
                }
            }
        } catch (Exception e) {
            logger.warning("Error updating customer details: " + e.getMessage());
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

    /**
     * Sets the command executor for this panel.
     *
     * @param executor the command executor to use
     */
    public void setCommandExecutor(CommandExecutor executor) {
        this.commandExecutor = executor;
    }
}
