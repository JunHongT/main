package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Tag;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.testutil.TypicalEateries.getTypicalAddressBook;
import static seedu.address.testutil.TypicalFeeds.getTypicalFeedList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EATERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EATERY;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class SaveTodoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalFeedList(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalFeedList(), new UserPrefs());
        if (model.isMainMode()) {
            model.toggle();
        }
    }

    @Test
    public void execute_validIndex_success() {
        Eatery eateryToSave = model.getFilteredTodoList().get(INDEX_FIRST_EATERY.getZeroBased());
        SaveTodoCommand saveTodoCommand = new SaveTodoCommand(INDEX_FIRST_EATERY);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getFeedList(), new UserPrefs());
        expectedModel.toggle();
        expectedModel.deleteEatery(eateryToSave);

        String name = eateryToSave.getName().fullName;
        String address = eateryToSave.getAddress().value;
        StringBuilder tags = new StringBuilder();
        for (Tag tag : eateryToSave.getTags()) {
            tags.append(PREFIX_TAG + " " + tag.getName() + " ");
        }
        String expectedMessage = String.format("add %s %s %s %s %s %s",
            PREFIX_NAME, name, PREFIX_ADDRESS, address, tags.toString(), PREFIX_CATEGORY);

        assertCommandSuccess(saveTodoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTodoList().size() + 1);
        SaveTodoCommand saveTodoCommand = new SaveTodoCommand(outOfBoundIndex);

        assertCommandFailure(saveTodoCommand, model, Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidMode_throwsCommandException() {
        SaveTodoCommand saveTodoCommand = new SaveTodoCommand(INDEX_FIRST_EATERY);
        model.toggle();
        assertCommandFailure(saveTodoCommand, model, SaveTodoCommand.MESSAGE_INVALID_MODE);
    }

    @Test
    public void equals() {
        SaveTodoCommand saveFirstCommand = new SaveTodoCommand(INDEX_FIRST_EATERY);
        SaveTodoCommand saveSecondCommand = new SaveTodoCommand(INDEX_SECOND_EATERY);

        // same object -> returns true
        assertTrue(saveFirstCommand.equals(saveFirstCommand));

        // same values -> returns true
        SaveTodoCommand saveFirstCommandCopy = new SaveTodoCommand(INDEX_FIRST_EATERY);
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy));

        // different types -> returns false
        assertFalse(saveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(saveFirstCommand.equals(null));

        // different eatery -> returns false
        assertFalse(saveFirstCommand.equals(saveSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEatery(Model model) {
        model.updateFilteredEateryList(p -> false);

        assertTrue(model.getFilteredEateryList().isEmpty());
    }
}
