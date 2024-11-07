package bizbook.ui;

import java.util.logging.Logger;

import bizbook.commons.core.LogsCenter;
import bizbook.logic.commands.AddCommand;
import bizbook.logic.commands.AddNoteCommand;
import bizbook.logic.commands.DeleteCommand;
import bizbook.logic.commands.EditCommand;
import bizbook.logic.commands.FindCommand;
import bizbook.logic.commands.ViewCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * Panel containing the list of command help.
 */
public class CommandTablePanel extends UiPart<Region> {
    private static final String FXML = "CommandTablePanel.fxml";

    private static final double COMMAND_WORD_RATIO = 0.15;
    private static final double COMMAND_USAGE_RATIO = 0.85;

    private static final double COMMAND_WORD_COLUMN_SIZE = Integer.MAX_VALUE * COMMAND_WORD_RATIO; // 15% of table width
    private static final double COMMAND_USAGE_COLUMN_SIZE = Integer.MAX_VALUE * COMMAND_USAGE_RATIO; // 85% of table width

    private final Logger logger = LogsCenter.getLogger(CommandTablePanel.class);


    @FXML
    private TableView<CommandEntry> commandTable;

    @FXML
    private TableColumn<CommandEntry, String> actionColumn;

    @FXML
    private TableColumn<CommandEntry, String> formatColumn;

    /**
     * Creates a {@code CommandTablePanel}
     */
    public CommandTablePanel() {
        super(FXML);
        initializeTable();
        populateData();
    }

    /**
     * Initialize table formatting.
     */
    private void initializeTable() {
        commandTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        actionColumn.setCellValueFactory(new PropertyValueFactory<>("commandWord"));
        actionColumn.setMaxWidth(COMMAND_WORD_COLUMN_SIZE);
        actionColumn.setMinWidth(75);

        formatColumn.setCellValueFactory(new PropertyValueFactory<>("commandUsage"));
        formatColumn.setCellFactory(column -> new CommandTableCell());
        formatColumn.setMinWidth(100);
        formatColumn.setMaxWidth(COMMAND_USAGE_COLUMN_SIZE);

        commandTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            actionColumn.setPrefWidth(newWidth.doubleValue() * COMMAND_WORD_RATIO);
            formatColumn.setPrefWidth(newWidth.doubleValue() * COMMAND_USAGE_COLUMN_SIZE);
        });
    }

    /**
     * Fills up the table with the commands.
     *
     * @param commandList list of commands to print.
     */
    private void populateData() {
        logger.fine("Showing a few command format of the application.");
        ObservableList<CommandEntry> commandList = FXCollections.observableArrayList(
            // Add more commands here as needed
            new CommandEntry(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE),
            new CommandEntry(AddNoteCommand.COMMAND_WORD, AddNoteCommand.MESSAGE_USAGE),
            new CommandEntry(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE),
            new CommandEntry(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE),
            new CommandEntry(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE),
            new CommandEntry(ViewCommand.COMMAND_WORD, ViewCommand.MESSAGE_USAGE)
        );
        commandTable.setItems(commandList);
    }

    /**
     * Custom table record that displays onto table.
     */
    public class CommandEntry {
        private String commandWord;
        private String commandUsage;

        /**
         * Creates a {@code CommandEntry} with the given command word and usage string.
         */
        public CommandEntry(String commandWord, String commandUsage) {
            this.commandWord = commandWord;
            this.commandUsage = commandUsage;
        }

        /**
         * Gets the command word.
         *
         * @return command word.
         */
        public String getCommandWord() {
            return this.commandWord;
        }

        /**
         * Gets the command usage syntax.
         *
         * @return command usage.
         */
        public String getCommandUsage() {
            return this.commandUsage;
        }
    }

    /**
     * Custom {@code TableCell} that displays the graphics of a command record.
     */
    class CommandTableCell extends TableCell<CommandEntry, String> {
        private final Text text = new Text();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                text.setText(item);
                text.wrappingWidthProperty()
                        .bind(getTableColumn().widthProperty().subtract(10));
                setGraphic(text);
            }
        }
    };
}
