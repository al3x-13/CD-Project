package cd.project.frontend.controllers;

import cd.project.frontend.Router;
import cd.project.frontend.components.AppMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private VBox container;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button submit;
    @FXML
    private Button home;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppMenu menu = new AppMenu();
        container.getChildren().addFirst(menu);

        username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleSubmit();
            }
        });

        password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleSubmit();
            }
        });

        submit.setOnAction(actionEvent -> handleSubmit());

        home.setOnAction(actionEvent -> Router.navigateToHome());
    }

    private void handleSubmit() {
        System.out.println("user: " + username.getText() + ", pw: " + password.getText());
    }
}