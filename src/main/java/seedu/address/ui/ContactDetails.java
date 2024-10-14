package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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

    private Person person;

    @FXML
    private HBox contactDetailPlane;

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

    /**
     * Creates a {@code ContactDetailPannel} with the given {@code Person} information.
     */
    public ContactDetails(Person person) {
        super(FXML);
    }

    /**
     * Sets the person object as the contact to be displayed on the panel.
     *
     * @param person The person object to be updated onto the panel.
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Updates the contact details panel with person info.
     */
    public void updatePanel() {
        this.clearPanel();
        this.setPanelInformation();
    }

    /**
     * Clears any previous contact details from the panel.
     */
    private void clearPanel() {
        // Clear existing labels
        name.setText("");
        phoneNo.setText("Mobile: ");
        email.setText("Email: ");
        address.setText("Address: ");
        notes.setText("Notes: ");
        notesList.getChildren().clear();
    }

    /**
     * Adds the contact details of the person into the panel.
     */
    private void setPanelInformation() {
        // Update with new person details if person is not null
        if (person != null) {
            logger.info("Displayig info of " + person.toString());

            name.setText(name.getText() + person.getName().fullName);
            phoneNo.setText(phoneNo.getText() + person.getPhone().toString());
            email.setText(email.getText() + person.getEmail().toString());
            address.setText(address.getText() + person.getAddress().toString());
        }
    }

}
