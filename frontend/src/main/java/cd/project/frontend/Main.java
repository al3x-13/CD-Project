package cd.project.frontend;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private static Stage rootStage;

    @Override
    public void start(Stage stage) throws IOException {
        rootStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scenes/home.fxml"));
        Parent root = fxmlLoader.load();

        // Initial scene setup
        Scene home = new Scene(root, 800, 600);
        stage.setTitle("CD");
        stage.setScene(home);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getRootStage() {
        return rootStage;
    }
}