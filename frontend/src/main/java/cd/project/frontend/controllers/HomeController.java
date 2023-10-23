package cd.project.frontend.controllers;

import cd.project.frontend.Router;
import cd.project.frontend.components.AppMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private VBox container;
    @FXML
    private Button login;
    @FXML
    private Button register;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.getChildren().addFirst(new AppMenu());

        login.setOnAction(actionEvent -> Router.navigateToLogin());
        register.setOnAction(actionEvent -> Router.navigateToRegister());
    }
}
