package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a remark to an existing person in the address book.
 */
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
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToRemark = lastShownList.get(index.getZeroBased());
        Person remarkedPerson = createRemarkedPerson(personToRemark, remark);
        model.setPerson(personToRemark, remarkedPerson);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, Messages.format(personToRemark)));
    }

    private static Person createRemarkedPerson(Person personToAddRemark, String remark) {
        assert personToAddRemark != null;

        return new Person(
                personToAddRemark.getName(),
                personToAddRemark.getPhone(),
                personToAddRemark.getEmail(),
                personToAddRemark.getAddress(),
                personToAddRemark.getTags(),
                remark);
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
