package cd.project.client.ui.components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ReservationsTable extends TableView {
    private int entriesPerPage;

    public ReservationsTable(int entriesPerPage) {
        this.entriesPerPage = entriesPerPage;

        // Component properties
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setStyle(
            "-fx-background-color: #333639;" +
            "-fx-border-radius: 3; " +
            "-fx-border-width: 1;" +
            "-fx-border-color: #5E656A;"
        );

        TableColumn test1 = new TableColumn("Test1");
        TableColumn test2 = new TableColumn("Test2");
        TableColumn test3 = new TableColumn("Test3");

        getColumns().addAll(test1, test2, test3);
    }

    public void populateTableWithTestData() {
        // TODO
    }
}
