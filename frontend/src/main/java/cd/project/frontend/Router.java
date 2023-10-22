package cd.project.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Router {
    private final Stage rootStage = Main.getRootStage();

    private void loadAndSetScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent page = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(page, 800, 600);
            stage.setScene(scene);
            Stage rootStage = Main.getRootStage();
            rootStage.hide();
            rootStage.setScene(scene);
            rootStage.show();
        } catch (IOException e) {
            System.out.println("Failed to change scene");
            e.printStackTrace();
        }
    }

    @FXML
    public void navigateToHome() {
        loadAndSetScene("home.fxml");
    }

    @FXML
    public void navigateToAbout() {
        loadAndSetScene("about.fxml");
    }
}
