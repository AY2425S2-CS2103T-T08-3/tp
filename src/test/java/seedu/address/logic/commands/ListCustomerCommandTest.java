package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class ListCustomerCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_listCustomer_success() {
        assertCommandSuccess(new ListCustomerCommand(), model, ListCustomerCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
