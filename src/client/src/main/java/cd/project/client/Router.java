package cd.project.client;

import cd.project.client.core.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Router {
    private static ArrayList<String> noAuthScenes = new ArrayList<>(Arrays.asList(
            "home.fxml",
            "login.fxml",
            "register.fxml",
            "about.fxml"
    ));

    private static void loadAndSetScene(String fxmlFile) {
        String fxmlFileToLoad = getSceneToLoadBasedOnAuthState(fxmlFile);

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("scenes/" + fxmlFileToLoad));
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

    // Redirects (by loading) 'Home' scene if user is not authenticated and is trying to access a page that requires auth
    private static String getSceneToLoadBasedOnAuthState(String fxmlFile) {
        return !UserSession.isAuthenticated() && !noAuthScenes.contains(fxmlFile) ?
                "home.fxml" :
                fxmlFile;
    }

    @FXML
    public static void navigateToHome() {
        loadAndSetScene("home.fxml");
    }

    @FXML
    public static void navigateToAbout() {
        loadAndSetScene("about.fxml");
    }

    @FXML
    public static void navigateToLogin() {
        loadAndSetScene("login.fxml");
    }

    @FXML
    public static void navigateToRegister() {
        loadAndSetScene("register.fxml");
    }

    @FXML
    public static void navigateToDashboard() {
        loadAndSetScene("dashboard.fxml");
    }
}
