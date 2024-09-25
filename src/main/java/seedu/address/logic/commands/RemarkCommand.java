package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class RemarkCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Added remark to Person: %1$s";

    private final Index index;
    private final String remark;

    /**
     * @param index of the person in the filtered person list to add a remark to
     * @param remark remark to add to the person
     */
    public RemarkCommand(Index index, String remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // TODO: Add implementation
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand otherRemarkCommand = (RemarkCommand) other;
        return index.equals(otherRemarkCommand.index)
                && remark.equals(otherRemarkCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("remark", remark)
                .toString();
    }
}
