package cd.project.client.ui.controllers;

import cd.project.client.Router;
import cd.project.client.ui.components.AppMenu;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class RegisterController implements Initializable {
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
    private Text usernameConstraint;
    @FXML
    private Text passwordConstraint;
    private final BooleanProperty validUsername = new SimpleBooleanProperty(false);
    private final BooleanProperty validPassword = new SimpleBooleanProperty(false);
    private final BooleanProperty validCredentials = new SimpleBooleanProperty(true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.getChildren().addFirst(new AppMenu());

        Platform.runLater(() -> username.requestFocus());

        username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleSubmit();
            }
        });

        // Only allow alphanumeric characters on input field
        UnaryOperator<TextFormatter.Change> alphanumericFilter = change -> {
            String text = change.getText();
            String regex = "[a-z0-9]*";

            if (text.isEmpty() || text.matches(regex)) {
                return change;
            }
            return null;
        };

        // alphanumeric input validation
        username.setTextFormatter(new TextFormatter<>(alphanumericFilter));

        // username validation
        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                String originalInputStyle = username.getStyle();

                if (newValue.length() >= 4 && newValue.length() <= 15) {
                    username.setStyle(originalInputStyle + "-fx-border-radius: 6; -fx-border-color: green;");
                    usernameConstraint.setFill(Color.GREEN);
                    validUsername.set(true);
                } else {
                    username.setStyle(originalInputStyle + "-fx-border-radius: 6; -fx-border-color: red;");
                    usernameConstraint.setFill(Color.RED);
                    validUsername.set(false);
                }
                validCredentials.set(!validUsername.get() || !validPassword.get());
            }
        });

        password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleSubmit();
            }
        });

        // password validation
        password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                String originalInputStyle = password.getStyle();

                if (newValue.length() >= 4 && newValue.length() <= 15) {
                    password.setStyle(originalInputStyle + "-fx-border-color: green;");
                    passwordConstraint.setFill(Color.GREEN);
                    validPassword.set(true);
                } else {
                    password.setStyle(originalInputStyle + "-fx-border-color: red;");
                    passwordConstraint.setFill(Color.RED);
                    validPassword.set(false);
                }
                validCredentials.set(!validUsername.get() || !validPassword.get());
            }
        });

        submit.disableProperty().bind(validCredentials);
        submit.setOnAction(actionEvent -> handleSubmit());

        home.setOnAction(actionEvent -> Router.navigateToHome());
    }

    private void handleSubmit() {
        System.out.println("user: " + username.getText() + ", pw: " + password.getText());
    }
}
