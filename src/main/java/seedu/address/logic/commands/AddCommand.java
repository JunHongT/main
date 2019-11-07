package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Eatery;

/**
 * Adds a eatery to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE_MAIN = COMMAND_WORD + ": Adds a eatery to the address book. "
            + "Parameters: "
            + PREFIX_NAME + " NAME "
            + PREFIX_ADDRESS + " ADDRESS "
            + PREFIX_CATEGORY + " CATEGORY "
            + "[" + PREFIX_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Inspirasi Stall "
            + PREFIX_ADDRESS + " Blk 207 New Upper Changi Road, #01-11"
            + PREFIX_CATEGORY + " Malay "
            + PREFIX_TAG + " cheap "
            + PREFIX_TAG + " nice";

    public static final String MESSAGE_USAGE_TODO = COMMAND_WORD + ": Adds a eatery to the todo list. "
        + "Parameters: "
        + PREFIX_NAME + " NAME "
        + PREFIX_ADDRESS + " ADDRESS "
        + "[" + PREFIX_TAG + " TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + " Inspirasi Stall "
        + PREFIX_ADDRESS + " Blk 207 New Upper Changi Road, #01-11, "
        + PREFIX_TAG + " cheap "
        + PREFIX_TAG + " nice";

    public static final String MESSAGE_SUCCESS = "New eatery added: %1$s";
    public static final String MESSAGE_DUPLICATE_EATERY = "This eatery already exists in the address book";

    private final Eatery toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Eatery}
     */
    public AddCommand(Eatery eatery) {
        requireNonNull(eatery);
        toAdd = eatery;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEatery(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EATERY);
        }

        model.addEatery(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
