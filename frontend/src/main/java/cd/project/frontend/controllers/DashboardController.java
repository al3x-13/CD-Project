package cd.project.frontend.controllers;

import cd.project.frontend.components.AppMenu;
import cd.project.frontend.components.DashboardLayout;
import cd.project.frontend.components.ReservationsTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private VBox container;
    @FXML
    private VBox reservations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.getChildren().addFirst(new AppMenu());
        container.getChildren().add(new DashboardLayout());
        // reservations.getChildren().add(new ReservationsTable());
    }
}
