package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.DrinkCatalog;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.drink.Drink;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.CustomerId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RewardPoints;
import seedu.address.model.person.TotalSpent;
import seedu.address.model.person.VisitCount;

public class PurchaseCommandTest {

    private static final String VALID_DRINK_NAME = "Matcha";
    private static final double VALID_DRINK_PRICE = 4.50;
    private static final String INVALID_DRINK_NAME = "Nonexistent Drink";
    private static final boolean IS_REDEMPTION = true;
    private static final boolean NOT_REDEMPTION = false;
    private static final int POINTS_PER_DOLLAR = 10;
    private static final int POINTS_TO_DOLLAR_RATIO = 100;

    private Model model;
    private DrinkCatalog drinkCatalog;

    @BeforeEach
    public void setUp() {
        // Create a drink catalog with the test drink
        drinkCatalog = new DrinkCatalog();
        drinkCatalog.addDrink(new Drink(VALID_DRINK_NAME, VALID_DRINK_PRICE, "Tea"));

        // Create an address book with at least one customer
        AddressBook addressBook = new AddressBook();
        Customer testCustomer = new Customer(
            new Name("Test Customer"),
            new Phone("12345678"),
            new Email("test@example.com"),
            new Address("Test Address"),
            new Remark(""),
            new HashSet<>(),
            new CustomerId("C001"),
            new RewardPoints("500"), // Enough points for redemption
            new VisitCount("5"),
            new Drink("latte"),
            new TotalSpent("50.0")
        );
        addressBook.addCustomer(testCustomer);

        // Create the model with our address book and drink catalog
        model = new ModelManager(addressBook, new UserPrefs(), drinkCatalog);
    }

    @Test
    public void execute_validPurchase_success() throws Exception {
        PurchaseCommand purchaseCommand = new PurchaseCommand(INDEX_FIRST_PERSON, VALID_DRINK_NAME, NOT_REDEMPTION);

        Customer customerBefore = model.getFilteredCustomerList().get(0);
        double expectedNewTotalSpent = Double.parseDouble(customerBefore.getTotalSpent().value) + VALID_DRINK_PRICE;
        int expectedNewRewardPoints = Integer.parseInt(customerBefore.getRewardPoints().value)
                + (int) Math.floor(VALID_DRINK_PRICE * POINTS_PER_DOLLAR);

        CommandResult result = purchaseCommand.execute(model);

        Customer customerAfter = model.getFilteredCustomerList().get(0);
        assertEquals(String.format(
                PurchaseCommand.MESSAGE_PURCHASE_SUCCESS,
                customerBefore.getName(),
                VALID_DRINK_NAME,
                VALID_DRINK_PRICE,
                (int) Math.floor(VALID_DRINK_PRICE * POINTS_PER_DOLLAR),
                expectedNewRewardPoints,
                expectedNewTotalSpent), result.getFeedbackToUser());

        assertEquals(expectedNewTotalSpent, Double.parseDouble(customerAfter.getTotalSpent().value), 0.001);
        assertEquals(expectedNewRewardPoints, Integer.parseInt(customerAfter.getRewardPoints().value));
        assertEquals(Integer.parseInt(customerBefore.getVisitCount().value) + 1,
                Integer.parseInt(customerAfter.getVisitCount().value));
    }

    @Test
    public void execute_validRedemption_success() throws Exception {
        PurchaseCommand purchaseCommand = new PurchaseCommand(INDEX_FIRST_PERSON, VALID_DRINK_NAME, IS_REDEMPTION);

        Customer customerBefore = model.getFilteredCustomerList().get(0);
        int pointsNeeded = (int) Math.ceil(VALID_DRINK_PRICE * POINTS_TO_DOLLAR_RATIO);
        int expectedNewRewardPoints = Integer.parseInt(customerBefore.getRewardPoints().value) - pointsNeeded;
        // Total spent should remain the same for redemptions
        double expectedNewTotalSpent = Double.parseDouble(customerBefore.getTotalSpent().value);

        CommandResult result = purchaseCommand.execute(model);

        Customer customerAfter = model.getFilteredCustomerList().get(0);
        assertEquals(String.format(
                PurchaseCommand.MESSAGE_REDEMPTION_SUCCESS,
                customerBefore.getName(),
                VALID_DRINK_NAME,
                VALID_DRINK_PRICE,
                pointsNeeded,
                expectedNewRewardPoints), result.getFeedbackToUser());

        assertEquals(expectedNewTotalSpent, Double.parseDouble(customerAfter.getTotalSpent().value), 0.001);
        assertEquals(expectedNewRewardPoints, Integer.parseInt(customerAfter.getRewardPoints().value));
        assertEquals(Integer.parseInt(customerBefore.getVisitCount().value) + 1,
                Integer.parseInt(customerAfter.getVisitCount().value));
    }

    @Test
    public void execute_insufficientPoints_throwsCommandException() {
        // First clear the model and add a customer with insufficient points
        model = new ModelManager(new AddressBook(), new UserPrefs(), drinkCatalog);

        Customer testCustomer = new Customer(
            new Name("Poor Customer"),
            new Phone("12345678"),
            new Email("poor@example.com"),
            new Address("Poor Address"),
            new Remark(""),
            new HashSet<>(),
            new CustomerId("C002"),
            new RewardPoints("10"), // Not enough points
            new VisitCount("1"),
            new Drink("latte"),
            new TotalSpent("5.0")
        );

        model.addCustomer(testCustomer);

        PurchaseCommand purchaseCommand = new PurchaseCommand(INDEX_FIRST_PERSON, VALID_DRINK_NAME, IS_REDEMPTION);

        int pointsNeeded = (int) Math.ceil(VALID_DRINK_PRICE * POINTS_TO_DOLLAR_RATIO);
        String expectedMessage = String.format(
                PurchaseCommand.MESSAGE_INSUFFICIENT_POINTS,
                pointsNeeded,
                10);

        assertCommandFailure(purchaseCommand, model, expectedMessage);
    }

    @Test
    public void toString_validPurchaseCommand_correctFormat() {
        Index testIndex = Index.fromOneBased(1);
        String testDrink = "ICED LATTE";
        boolean testRedemption = true;
        PurchaseCommand command = new PurchaseCommand(testIndex, testDrink, testRedemption);

        String expected = new ToStringBuilder(command)
                .add("customerIndex", testIndex)
                .add("drinkName", testDrink)
                .add("isRedemption", testRedemption)
                .toString();

        assertEquals(expected, command.toString());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        PurchaseCommand purchaseCommand = new PurchaseCommand(outOfBoundIndex, VALID_DRINK_NAME, NOT_REDEMPTION);

        assertCommandFailure(purchaseCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidDrinkName_throwsCommandException() {
        PurchaseCommand purchaseCommand = new PurchaseCommand(INDEX_FIRST_PERSON, INVALID_DRINK_NAME, NOT_REDEMPTION);

        String expectedMessage = String.format(PurchaseCommand.MESSAGE_DRINK_NOT_FOUND, INVALID_DRINK_NAME);
        assertCommandFailure(purchaseCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        PurchaseCommand purchaseFirstCommand = new PurchaseCommand(INDEX_FIRST_PERSON,
            VALID_DRINK_NAME, NOT_REDEMPTION);
        PurchaseCommand purchaseSecondCommand = new PurchaseCommand(INDEX_SECOND_PERSON,
            VALID_DRINK_NAME, NOT_REDEMPTION);
        PurchaseCommand purchaseDifferentDrinkCommand = new PurchaseCommand(INDEX_FIRST_PERSON,
            "Green Tea", NOT_REDEMPTION);
        PurchaseCommand purchaseRedemptionCommand = new PurchaseCommand(INDEX_FIRST_PERSON,
            VALID_DRINK_NAME, IS_REDEMPTION);

        // same object -> returns true
        assertTrue(purchaseFirstCommand.equals(purchaseFirstCommand));

        // same values -> returns true
        PurchaseCommand purchaseFirstCommandCopy = new PurchaseCommand(INDEX_FIRST_PERSON,
            VALID_DRINK_NAME, NOT_REDEMPTION);
        assertTrue(purchaseFirstCommand.equals(purchaseFirstCommandCopy));

        // different index -> returns false
        assertFalse(purchaseFirstCommand.equals(purchaseSecondCommand));

        // different drink -> returns false
        assertFalse(purchaseFirstCommand.equals(purchaseDifferentDrinkCommand));

        // different redemption status -> returns false
        assertFalse(purchaseFirstCommand.equals(purchaseRedemptionCommand));

        // different types -> returns false
        assertFalse(purchaseFirstCommand.equals(1));

        // null -> returns false
        assertFalse(purchaseFirstCommand.equals(null));
    }
}
