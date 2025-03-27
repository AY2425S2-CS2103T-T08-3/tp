package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Staff;

/**
 * Panel containing the list of staff.
 */
public class StaffListPanel extends UiPart<Region> {
    private static final String FXML = "StaffListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StaffListPanel.class);

    @FXML
    private ListView<Staff> staffListView;

    // Handler for staff selection events
    private Consumer<Staff> selectionHandler;

    /**
     * Creates a {@code StaffListPanel} with the given {@code ObservableList}.
     */
    public StaffListPanel(ObservableList<Staff> staffList) {
        super(FXML);
        staffListView.setItems(staffList);
        staffListView.setCellFactory(listView -> new StaffListViewCell());

        // Add listener for staff selection
        staffListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && selectionHandler != null) {
                logger.fine("Selection changed to: " + newValue.getName().fullName);
                selectionHandler.accept(newValue);
            }
        });
    }

    /**
     * Sets the handler for staff selection events.
     *
     * @param handler the function to call when a staff is selected
     */
    public void setPersonSelectionHandler(Consumer<Staff> handler) {
        this.selectionHandler = handler;
    }

    /**
     * Selects the specified staff in the list view, if present.
     *
     * @param staff The staff to select
     * @return true if selection was successful, false if staff not found
     */
    public boolean selectStaff(Staff staff) {
        if (staff == null) {
            return false;
        }

        // Find the index of the staff in the list
        for (int i = 0; i < staffListView.getItems().size(); i++) {
            if (staffListView.getItems().get(i).equals(staff)) {
                staffListView.getSelectionModel().select(i);
                staffListView.scrollTo(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Staff} using a {@code StaffCard}.
     */
    class StaffListViewCell extends ListCell<Staff> {
        @Override
        protected void updateItem(Staff staff, boolean empty) {
            super.updateItem(staff, empty);

            if (empty || staff == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StaffCard(staff, getIndex() + 1).getRoot());
            }
        }
    }
}
