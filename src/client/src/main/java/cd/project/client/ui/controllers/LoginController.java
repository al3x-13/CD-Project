package cd.project.client.ui.controllers;

import cd.project.client.Main;
import cd.project.client.Router;
import cd.project.client.core.UserSession;
import cd.project.client.ui.components.AppMenu;
import cd.project.client.ui.components.ProtocolLabel;
import cd.project.client.ui.components.SessionExpireLabel;
import cd.project.client.ui.components.SuccessLabel;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
    @FXML
    private Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppMenu menu = new AppMenu();
        container.getChildren().addFirst(menu);
        container.getChildren().addLast(new ProtocolLabel());

        // autofocus username input
        Platform.runLater(() -> username.requestFocus());

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

        if (Main.sessionExpiredNotification) {
            new SessionExpireLabel(this.label);
            Main.sessionExpiredNotification = false;
        }

        home.setOnAction(actionEvent -> Router.navigateToHome());
    }

    private void handleSubmit() {
        if (UserSession.authenticate(username.getText(), password.getText())) {
            new SuccessLabel(this.label, "Loggin successful", true);
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(actionEvent -> Router.navigateToMyBookings());
            delay.play();
        } else {
            new SuccessLabel(this.label, "Invalid credentials", false);
        }
    }
}