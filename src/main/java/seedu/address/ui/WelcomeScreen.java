package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * The welcome screen of the app.
 */
public class WelcomeScreen extends UiPart<Region> {

    private static final String FXML = "WelcomeScreen.fxml";
    private static final Logger logger = LogsCenter.getLogger(WelcomeScreen.class);

    // The path to the coffee icon
    private static final String COFFEE_ICON_PATH = "docs/images/cafeconnect_graphics/cafeconnect-icon.png";

    private final Logic logic;
    private final Stage primaryStage;
    private final UiManager uiManager;

    @FXML
    private Button staffCustomerButton;

    @FXML
    private Button drinksMenuButton;

    @FXML
    private StackPane welcomePane;

    @FXML
    private ImageView logoIconView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label sloganLabel;

    @FXML
    private Label subtitleLabel;

    /**
     * Creates a welcome screen with the given parameters.
     */
    public WelcomeScreen(Stage primaryStage, Logic logic, UiManager uiManager) {
        super(FXML);
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.uiManager = uiManager;

        // Set the title
        primaryStage.setTitle("CafeConnect");

        // Set minimum dimensions
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        // Create scene
        Scene scene = new Scene(getRoot());

        // Add default stylesheets
        scene.getStylesheets().add("view/Extensions.css");
        scene.getStylesheets().add("view/CafeConnect.css");

        primaryStage.setScene(scene);
    }

    /**
     * Shows the welcome screen with a fade-in animation.
     */
    public void show() {
        logger.info("Showing welcome screen");
        primaryStage.show();

        // Add a fade-in transition for a more polished appearance
        try {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(800), welcomePane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        } catch (Exception e) {
            logger.warning("Could not play fade-in animation: " + e.getMessage());
            // Continue without animation - not critical
        }
    }

    @FXML
    private void initialize() {
        // Set a fallback style for welcomePane with a nice earthy color
        if (welcomePane != null) {
            welcomePane.getStyleClass().add("welcome-background");
            welcomePane.setStyle("-fx-background-color: #F2E8CF;"); // Warm earthy color as fallback
        }

        // Style the title and slogan
        if (titleLabel != null) {
            titleLabel.getStyleClass().add("welcome-title");
        }

        if (sloganLabel != null) {
            sloganLabel.getStyleClass().add("welcome-slogan");
        }

        // Safely initialize coffee icon
        if (logoIconView != null) {
            try {
                // Load the coffee icon image directly from the file path
                Image iconImage = new Image("file:" + COFFEE_ICON_PATH);

                if (!iconImage.isError()) {
                    logoIconView.setImage(iconImage);
                    logger.info("Successfully loaded coffee icon from: " + COFFEE_ICON_PATH);
                } else {
                    logger.warning("Failed to load coffee icon - will try alternative paths");
                }
            } catch (Exception e) {
                logger.warning("Could not load coffee icon: " + e.getMessage());
            }
        }

        // Set up button handlers with null checks
        if (staffCustomerButton != null) {
            staffCustomerButton.setOnAction(event -> handleStaffCustomerButtonAction());
        }

        if (drinksMenuButton != null) {
            drinksMenuButton.setOnAction(event -> handleDrinksMenuButtonAction());
        }
    }



    /**
     * Handles the staff/customer button action.
     */
    @FXML
    private void handleStaffCustomerButtonAction() {
        logger.info("Staff & Customer button clicked, transitioning to main application");

        // Hide the welcome screen
        primaryStage.hide();

        // Create and display the main window with Staff & Customer tabs
        MainWindow mainWindow = new MainWindow(primaryStage, logic);

        // Important: Set up the stage after construction but before using it
        mainWindow.setUpStage();

        mainWindow.show();

        // Fill in the components of the main window and select the Staff tab
        mainWindow.fillInnerParts();
        mainWindow.selectTab(0); // Select Staff tab (index 0)
    }

    /**
     * Handles the drinks menu button action.
     */
    @FXML
    private void handleDrinksMenuButtonAction() {
        logger.info("Drinks Menu button clicked, transitioning to drinks menu");

        // Hide the welcome screen
        primaryStage.hide();

        // Create and display the main window with Drinks tab
        MainWindow mainWindow = new MainWindow(primaryStage, logic);

        // Important: Set up the stage after construction but before using it
        mainWindow.setUpStage();

        mainWindow.show();

        // Fill in the components of the main window and select the Drinks tab
        mainWindow.fillInnerParts();
        mainWindow.selectTab(2); // Select Drinks tab (index 2, assuming it's the third tab)
    }
}
