package cd.project.frontend.controllers;

import cd.project.frontend.Router;
import cd.project.frontend.components.AppMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.getChildren().addFirst(new AppMenu());
    }

    @FXML
    private void handleMenuAbout() {
        Router router = new Router();
        router.navigateToAbout();
    }
}
