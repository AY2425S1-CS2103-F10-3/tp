package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;


/**
 * Controller for a contact details editor.
 */
public class ContactDetailsEditor extends UiPart<Region> {
    /**
     * Represents a function that can update a person
     */
    @FunctionalInterface
    public interface PersonEditor {
        void edit(Person previousPerson, Person newPerson);
    }

    private static final String FXML = "ContactDetailsEditor.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetailsEditor.class);

    private final BooleanProperty isEditorShown;
    private final Person person;
    private final Index personIndex;
    private final CommandExecutor commandExecutor;

    @FXML
    private Button saveChanges;
    @FXML
    private Button discardChanges;

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNoField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;



    /**
     * Creates a {@code ContactDetailsEditor} that can edit a {@code Person}.
     *
     * @param isEditorShown whether this editor should still be shown. Will be
     *                      set to false when this editor should be closed.
     * @param person the person to edit
     */
    public ContactDetailsEditor(
        BooleanProperty isEditorShown,
        Person person,
        Index personIndex,
        CommandExecutor commandExecutor
    ) {
        super(FXML);
        this.isEditorShown = isEditorShown;
        this.person = person;
        this.personIndex = personIndex;
        this.commandExecutor = commandExecutor;

        bindControlsToEditableProperty();
        setButtonTextAlwaysVisible();
        fillFieldsWithPerson(person);

        logger.info("Editing info of " + person);
    }

    private void fillFieldsWithPerson(Person person) {
        nameField.setText(person.getName().fullName);
        phoneNoField.setText(person.getPhone().value);
        emailField.setText(person.getEmail().value);
        addressField.setText(person.getAddress().value);
    }

    /**
     * Adjusts the buttons such that their text will always be visible
     * regardless of resizing.
     */
    private void setButtonTextAlwaysVisible() {
        // Set the buttons' min width to their preferred size, i.e. the size
        // they would have if the text within them can be fully displayed.
        saveChanges.setMinWidth(Control.USE_PREF_SIZE);
        discardChanges.setMinWidth(Control.USE_PREF_SIZE);
    }

    /**
     * Creates bindings between the button controls to `isEditable`
     */
    private void bindControlsToEditableProperty() {
        // Bind the buttons' managed property to their visibility.
        saveChanges.managedProperty().bindBidirectional(saveChanges.visibleProperty());
        discardChanges.managedProperty().bindBidirectional(discardChanges.visibleProperty());

        // Bind the visibilities of the controls to the isEditorShown property
        saveChanges.visibleProperty().bind(isEditorShown);
        discardChanges.visibleProperty().bind(isEditorShown);
    }


    @FXML
    void saveChanges() {
        String command = EditCommand.COMMAND_WORD + " " + personIndex.getOneBased()
            + " " + PREFIX_NAME + nameField.getText()
            + " " + PREFIX_PHONE + phoneNoField.getText()
            + " " + PREFIX_EMAIL + emailField.getText()
            + " " + PREFIX_ADDRESS + addressField.getText();
        try {
            commandExecutor.execute(command);
        } catch (CommandException | ParseException e) {
            logger.info("Failed to save changes with command " + command);
            return;
        }

        logger.info("Saved changes for " + person);
        isEditorShown.set(false);
    }

    @FXML
    void discardChanges() {
        fillFieldsWithPerson(person);
        logger.info("Discarded changes for " + person);
        isEditorShown.set(false);
    }


}
