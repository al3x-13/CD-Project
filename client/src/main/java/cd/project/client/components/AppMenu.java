package cd.project.client.components;

import cd.project.client.Router;
import cd.project.client.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class AppMenu extends MenuBar {
    private final String backgroundColor = "#50565A";

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

        // Adds unprotected menu items
        application.getItems().addAll(home, about);

        // Protected (auth required) buttons
        if (UserSession.isAuthenticated()) {
            MenuItem dashboard = new MenuItem("Dashboard");
            dashboard.setOnAction(actionEvent -> Router.navigateToDashboard());

            // Adds protected menu items
            application.getItems().add(dashboard);
        }
        getMenus().add(application);
    }
}
