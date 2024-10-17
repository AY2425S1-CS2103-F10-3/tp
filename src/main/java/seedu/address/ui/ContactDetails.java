package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Controller for the contact details panel.
 */
public class ContactDetails extends UiPart<Region> {
    private static final String FXML = "ContactDetails.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetails.class);

    private final BooleanProperty isEditorShown = new SimpleBooleanProperty(false);
    private Person person;

    @FXML
    private VBox contactDetailsPanel;

    @FXML
    private Label name;
    @FXML
    private Label phoneNo;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label notes;
    @FXML
    private VBox notesList;

    @FXML
    private Button edit;
    @FXML
    private Button saveChanges;
    @FXML
    private Button discardChanges;
    @FXML
    private StackPane editorPlaceholder;

    /**
     * Creates a {@code ContactDetailsPanel}
     */
    public ContactDetails() {
        super(FXML);
        bindControlsToEditableProperty();
        setButtonTextAlwaysVisible();
    }

    /**
     * Creates bindings between the button controls to `isEditable`
     */
    private void bindControlsToEditableProperty() {
        // Bind the controls' managed property to their visibility.
        // This means that when a button is invisible, it should not take up any
        // space when laying out the controls.
        edit.managedProperty().bindBidirectional(edit.visibleProperty());
        saveChanges.managedProperty().bindBidirectional(saveChanges.visibleProperty());
        discardChanges.managedProperty().bindBidirectional(discardChanges.visibleProperty());
        editorPlaceholder.managedProperty().bindBidirectional(editorPlaceholder.visibleProperty());

        // Bind the visibilities of the controls to the isEditable property
        edit.visibleProperty().bind(isEditorShown.not());
        saveChanges.visibleProperty().bind(isEditorShown);
        discardChanges.visibleProperty().bind(isEditorShown);
        editorPlaceholder.visibleProperty().bind(isEditorShown);
    }

    /**
     * Adjusts the buttons such that their text will always be visible
     * regardless of resizing.
     */
    private void setButtonTextAlwaysVisible() {
        // Set the button's min width to their preferred size, i.e. the size
        // they would have if the text within them can be fully displayed.
        edit.setMinWidth(Control.USE_PREF_SIZE);
        saveChanges.setMinWidth(Control.USE_PREF_SIZE);
        discardChanges.setMinWidth(Control.USE_PREF_SIZE);
    }

    /**
     * Loads ContactDetailsEditor into the controlDetailsEditorPlaceholder control
     */
    private void loadEditor(Person person) {
        requireNonNull(person);

        ContactDetailsEditor detailsEditor = new ContactDetailsEditor(person);
        editorPlaceholder.getChildren().add(detailsEditor.getRoot());
    }

    /**
     * Sets the person object as the contact to be displayed on the panel.
     *
     * @param person The person object to be updated onto the panel.
     */
    public void setPerson(Person person) {
        this.person = person;
        clearPanel();
        setPanelInformation();
        loadEditor(person);
    }

    /**
     * Clears any previous contact details from the panel.
     */
    private void clearPanel() {
        // Clear existing labels
        name.setText("");
        phoneNo.setText("");
        email.setText("");
        address.setText("");
        notes.setText("");
        notesList.getChildren().clear();
    }

    /**
     * Adds the contact details of the person into the panel.
     */
    private void setPanelInformation() {
        // Update with new person details if person is not null
        if (person != null) {
            logger.info("Displaying info of " + person.toString());

            name.setText(person.getName().fullName);
            phoneNo.setText(person.getPhone().value);
            email.setText(person.getEmail().value);
            address.setText(person.getAddress().value);

            if (!person.getNotes().isEmpty()) {
                Label notesHeader = new Label("Notes");
                notesHeader.setId("notes-header");
                notesList.getChildren().add(notesHeader);
                person.getNotes().stream()
                        .sorted(Comparator.comparing(note -> note.getNote()))
                        .forEach(note -> {
                            Label label = new Label("• " + note.getNote());
                            label.setId("notes-label");
                            notesList.getChildren().add(label);
                        });
            }
        }
    }

    @FXML
    private void showEditor() {
        isEditorShown.set(true);
    }

    @FXML
    private void discardEditChanges() {
        isEditorShown.set(false);
    }

    @FXML
    private void saveEditChanges() {
        isEditorShown.set(false);
        // TODO: Save the changes to the person
    }
}
