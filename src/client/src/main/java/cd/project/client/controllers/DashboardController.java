package cd.project.client.controllers;

import cd.project.client.components.AppMenu;
import cd.project.client.components.DashboardLayout;
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
