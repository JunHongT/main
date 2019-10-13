package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the eatery identified by the name of the eatery."
            + "Parameters: "  + PREFIX_NAME + " NAME \n"
            + "Example: " + COMMAND_WORD + " \\n Two Chefs Eating Place";


    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Eatery: %1$s";


    private final Name targetName;

    public DeleteCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        for (int i = 0; i < lastShownList.size(); i++) {
            Person curr = lastShownList.get(i);
            if (curr.getName().equals(targetName)) {
                model.deletePerson(curr);
                break;
            }

            if (i >= lastShownList.size() - 1) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME_DISPLAYED);
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetName.equals(((DeleteCommand) other).targetName)); // state check
    }
}
