package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Controller for the contact details panel.
 */
public class ContactDetails extends UiPart<Region> {

    private static final String FXML = "ContactDetails.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetails.class);

    private BooleanProperty isEditable = new SimpleBooleanProperty(false);

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

    /**
     * Creates a {@code ContactDetailsPanel} with the given {@code Person} information.
     */
    public ContactDetails(Person person) {
        super(FXML);
        this.person = person;
        bindButtonToEditableProperty();
        setButtonMinWidth();
    }

    /**
     * Creates bindings between the button controls to `isEditable`
     */
    private void bindButtonToEditableProperty() {
        // Bind the button's managed property to their visibility.
        // This means that when a button is invisible, it should not take up any
        // space when laying out the controls.
        edit.managedProperty().bindBidirectional(edit.visibleProperty());
        saveChanges.managedProperty().bindBidirectional(saveChanges.visibleProperty());
        discardChanges.managedProperty().bindBidirectional(discardChanges.visibleProperty());

        // Bind the visibilities of the button to the isEditable property
        edit.visibleProperty().bind(isEditable.not());
        saveChanges.visibleProperty().bind(isEditable);
        discardChanges.visibleProperty().bind(isEditable);
    }

    private void setButtonMinWidth() {
        // Set the button's min width to their preferred size, i.e. the size
        // they would have if the text within them can be fully displayed.
        edit.setMinWidth(Control.USE_PREF_SIZE);
        saveChanges.setMinWidth(Control.USE_PREF_SIZE);
        discardChanges.setMinWidth(Control.USE_PREF_SIZE);
    }

    /**
     * Sets the person object as the contact to be displayed on the panel.
     *
     * @param person The person object to be updated onto the panel.
     */
    public void setPerson(Person person) {
        this.person = person;
        this.clearPanel();
        this.setPanelInformation();
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
    private void makeFieldsEditable() {
        isEditable.set(true);
    }

    @FXML
    private void discardEditChanges() {
        isEditable.set(false);
    }

    @FXML
    private void saveEditChanges() {
        isEditable.set(false);
        // TODO: Save the changes to the person
    }
}
