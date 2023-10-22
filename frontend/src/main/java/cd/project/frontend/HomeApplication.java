package cd.project.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource("home.fxml"));
        Scene home = new Scene(fxmlLoader.load(), 800, 600);
        HomeController controller = fxmlLoader.getController();
        stage.setTitle("CD");
        stage.setScene(home);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}