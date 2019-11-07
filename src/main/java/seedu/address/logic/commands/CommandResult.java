package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.eatery.Eatery;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final Eatery eateryToShow;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The application should save to-do eatery to eatery list.
     */
    private final boolean wantToSave;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Eatery eateryToShow,
                         boolean showHelp, boolean exit, boolean wantToSave) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.eateryToShow = eateryToShow;
        this.showHelp = showHelp;
        this.exit = exit;
        this.wantToSave = wantToSave;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, null, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code eateryToShow},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Eatery eateryToShow) {
        this(feedbackToUser, eateryToShow, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields,
     * and {@code eateryToShow} set to its default value
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean wantToSave) {
        this(feedbackToUser, null, showHelp, exit, wantToSave);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Eatery getEateryToShow() {
        return eateryToShow;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean wantToSave() {
        return wantToSave;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && (Objects.equals(eateryToShow, otherCommandResult.eateryToShow))
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, eateryToShow, showHelp, exit);
    }

}
