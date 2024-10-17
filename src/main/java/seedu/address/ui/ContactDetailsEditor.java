package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Controller for a contact details editor.
 */
public class ContactDetailsEditor extends UiPart<Region> {
    private static final String FXML = "ContactDetailsEditor.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetailsEditor.class);

    private final Person person;

    @FXML
    private Button saveChanges;
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
     */
    public ContactDetailsEditor(Person person) {
        super(FXML);
        this.person = person;
        fillFieldsWithPerson(person);
    }

    private void fillFieldsWithPerson(Person person) {
        nameField.setText(person.getName().fullName);
        phoneNoField.setText(person.getPhone().value);
        emailField.setText(person.getEmail().value);
        addressField.setText(person.getAddress().value);
    }

    private void saveChanges() {
        Person newPerson = new Person(
            new Name(nameField.getText()),
            new Phone(phoneNoField.getText()),
            new Email(emailField.getText()),
            new Address(addressField.getText()),
            person.getTags(),
            person.getNotes()
        );
        // TODO: Save the values
    }

    private void discardChanges() {
        fillFieldsWithPerson(person);
    }
}
