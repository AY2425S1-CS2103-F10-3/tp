package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;

/**
 * Changes or adds the notes of an existing person in BizBook.
 */
public class NotesCommand extends Command {

    public static final String COMMAND_WORD = "notes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the notes of the person identified "
            + "by the index number used in the last person listing. "
            + "New note will be appended to the notes currently stored."
            + " To delete notes of a person, input 'delete' after n/ instead of a note\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "n/ [NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "n/ High profile client.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Notes: %2$s";

    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Added notes to Person: %1$s";
    public static final String MESSAGE_DELETE_NOTES_SUCCESS = "Removed notes from Person: %1$s";
    public static final String DELETE_COMMAND_KEYWORD = "delete";

    private final Index index;
    private final String note;


    /**
     * @param index of the person in the filtered person list to edit the notes
     * @param note of the person to be updated to
     */
    public NotesCommand(Index index, String note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Update notes with new note
        Notes notesToEdit = personToEdit.getNotes();
        notesToEdit.add(note);

        if (note.equalsIgnoreCase(DELETE_COMMAND_KEYWORD)) {
            notesToEdit.clear();
        } else {
            notesToEdit.add(note);
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), notesToEdit);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !note.isEmpty() ? MESSAGE_ADD_NOTES_SUCCESS : MESSAGE_DELETE_NOTES_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }

}
