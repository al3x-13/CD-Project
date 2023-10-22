package cd.project.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class HomeController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button submit;
    @FXML
    private Label testLabel;


    @FXML
    public void initialize() {
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
    }

    @FXML
    private void handleSubmit() {
        testLabel.setText("user: " + username.getText() + ", pw: " + password.getText());
    }

    @FXML
    private void handleMenuAbout() {
        Router router = new Router();
        router.navigateToAbout();
    }
}