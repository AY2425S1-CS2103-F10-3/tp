package bizbook.logic.commands.exporter;

import static bizbook.testutil.TypicalPersons.AMY;
import static bizbook.testutil.TypicalPersons.BOB;
import static bizbook.testutil.TypicalPersons.CHARLIE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import bizbook.commons.util.FileUtil;
import bizbook.logic.commands.exporter.exceptions.EmptyAddressBookException;
import bizbook.model.AddressBook;
import bizbook.model.UserPrefs;


public class CsvExporterTest {
    private static final String CHARLIE_VCF_FILE_PATH = "charlie.csv";
    private static final String PEOPLE_VCF_FILE_PATH = "people.csv";

    @TempDir
    public Path temporaryFolder;

    private CsvExporter csvExporter;

    @BeforeEach
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setExportDirectoryPath(temporaryFolder.resolve("exports"));
        csvExporter = new CsvExporter(userPrefs);
    }

    private Path getTestFilePath(String fileName) {
        String path = "CsvExporterTest/" + fileName;
        try {
            URL url = CsvExporterTest.class.getResource(path);
            assertNotNull(url, path + " does not exist.");
            return Path.of(url.toURI());
        } catch (URISyntaxException e) {
            fail("Failed to get URI for resource: " + path);
            return null;
        }
    }

    private String readTestFile(String fileName) {
        try {
            return FileUtil.readFromFile(getTestFilePath(fileName));
        } catch (IOException ie) {
            fail("Failed to load resource: " + fileName);
            return null;
        }
    }

    @Test
    public void export_empty_throwsEmptyAddressBookException() {
        // Assert that the file does not exist at first
        Path exportPath = csvExporter.getExportPath();
        assertFalse(FileUtil.isFileExists(exportPath));

        AddressBook addressBook = new AddressBook();
        assertThrows(EmptyAddressBookException.class, () -> csvExporter.exportAddressBook(addressBook));
        assertFalse(FileUtil.isFileExists(exportPath));
    }

    @Test
    public void export_onePerson_createsPersonVcf() {
        // Assert that the file does not exist at first
        Path exportPath = csvExporter.getExportPath();
        assertFalse(FileUtil.isFileExists(exportPath));

        // Export creates a file with the person's details
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(CHARLIE);
        assertDoesNotThrow(() -> csvExporter.exportAddressBook(addressBook));

        // Assert that the file exists now
        assertTrue(FileUtil.isFileExists(exportPath));
        try {
            String expectedValue = readTestFile(CHARLIE_VCF_FILE_PATH);
            String actualValue = FileUtil.readFromFile(exportPath);

            assertEquals(expectedValue, actualValue);
        } catch (IOException ie) {
            fail(ie);
        }
    }

    @Test
    public void export_multiplePeople_createsPeopleVcf() {
        // Assert that the file does not exist at first
        Path exportPath = csvExporter.getExportPath();
        assertFalse(FileUtil.isFileExists(exportPath));

        // Export creates a file with the person's details
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(AMY);
        addressBook.addPerson(BOB);
        addressBook.addPerson(CHARLIE);
        assertDoesNotThrow(() -> csvExporter.exportAddressBook(addressBook));

        // Assert that the file exists now
        assertTrue(FileUtil.isFileExists(exportPath));
        try {
            String expectedValue = readTestFile(PEOPLE_VCF_FILE_PATH);
            String actualValue = FileUtil.readFromFile(exportPath);

            assertEquals(expectedValue, actualValue);
        } catch (IOException ie) {
            fail(ie);
        }
    }
}
