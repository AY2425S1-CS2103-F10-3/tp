package seedu.address.ui;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class ContactListTest {

    private static CountDownLatch latch = new CountDownLatch(1);
    private ObservableList<Person> personList;

    // Dummy JavaFX application
    public static class TestApp extends Application {
        @Override
        public void start(javafx.stage.Stage primaryStage) {
            latch.countDown(); // Release the latch when the application starts
        }
    }

    @BeforeAll
    static void setUpOnce() throws Exception {
        // Start the JavaFX application
        new Thread(() -> Application.launch(TestApp.class)).start();
        // Wait for the application to start
        latch.await();
    }

    @Test
    void testComponentNotNull() throws TimeoutException, InterruptedException {

        this.personList = FXCollections.observableArrayList(TypicalPersons.getTypicalPersons());
        ContactList x = new ContactList(this.personList);

        // assertDoesNotThrow(() -> ersonList),
        //     "We should not be getting an exception");
    }

}