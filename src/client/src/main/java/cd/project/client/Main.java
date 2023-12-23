package cd.project.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private static Stage rootStage;
    public static String BACKGROUND_COLOR = "#25282a";
    public static String DRAWER_BACKGROUND_COLOR = "#35383a";
    public static String DRAWER_TEXT_COLOR = "#b8baba";
    public static String TITLE_COLOR_PRIMARY = "#377DFF";
    public static String TEXT_COLOR_PRIMARY = "#666D72";

    // User session
    private static final UserSession session = new UserSession();

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

    public static UserSession getUserSession() {
        return session;
    }
}