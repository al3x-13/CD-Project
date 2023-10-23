package cd.project.frontend.components;

import cd.project.frontend.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class AppMenu extends MenuBar {
    @FXML
    private MenuItem home;
    @FXML
    private MenuItem about;
    private final String backgroundColor = "#636363";

    public AppMenu() {
        // Components properties
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setStyle("-fx-background-color: " + backgroundColor + ";");

        // Menu items
        Menu application = new Menu("Application");
        MenuItem home = new MenuItem("Home");
        home.setOnAction(actionEvent -> Router.navigateToHome());
        MenuItem about = new MenuItem("About");
        about.setOnAction(actionEvent -> Router.navigateToAbout());
        application.getItems().addAll(home, about);

        // Adds menu
        getMenus().add(application);
    }
}
