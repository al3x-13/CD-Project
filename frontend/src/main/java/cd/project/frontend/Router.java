package cd.project.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Router {
    private final Stage rootStage = Main.getRootStage();

    private static void loadAndSetScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent page = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(page, 800, 600);
            stage.setScene(scene);
            Stage rootStage = Main.getRootStage();
            rootStage.setScene(scene);
            rootStage.show();
        } catch (IOException e) {
            System.out.println("Failed to change scene");
            e.printStackTrace();
        }
    }

    @FXML
    public static void navigateToHome() {
        loadAndSetScene("scenes/home.fxml");
    }

    @FXML
    public static void navigateToAbout() {
        loadAndSetScene("scenes/about.fxml");
    }

    @FXML
    public static void navigateToLogin() {
        loadAndSetScene("scenes/login.fxml");
    }

    @FXML
    public static void navigateToRegister() {
        loadAndSetScene("scenes/register.fxml");
    }

    @FXML
    public static void navigateToDashboard() {
        loadAndSetScene("scenes/dashboard.fxml");
    }
}
