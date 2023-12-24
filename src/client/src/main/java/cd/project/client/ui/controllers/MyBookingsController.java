package cd.project.client.ui.controllers;

import cd.project.client.ui.components.AppMenu;
import cd.project.client.ui.components.MyBookingsLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MyBookingsController implements Initializable {
    @FXML
    private VBox container;
    @FXML
    private VBox reservations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.getChildren().addFirst(new AppMenu());
        container.getChildren().add(new MyBookingsLayout());
        // reservations.getChildren().add(new ReservationsTable());
    }
}
