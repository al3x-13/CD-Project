package cd.project.client;

import cd.project.client.core.UserSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {
    private static Stage rootStage;
    public static String BACKGROUND_COLOR = "#25282a";
    public static String DRAWER_BACKGROUND_COLOR = "#35383a";
    public static String DRAWER_TEXT_COLOR = "#b8baba";
    public static String TITLE_COLOR_PRIMARY = "#377DFF";
    public static String TEXT_COLOR_PRIMARY = "#666D72";

    // User session
    private static final UserSession session = new UserSession();
    public static CommunicationProtocol clientProtocol;

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
        if (args.length != 2) {
            System.out.println("Missing arguments: please specify the communication protocol");
            System.out.println("Available communication protocols: 'soap' | 'rest'");
            System.out.println("Example usage: java Main.java <communication_protocol>");
            System.exit(1);
        }

        if (!Objects.equals(args[1].toLowerCase(), "soap") || !Objects.equals(args[1].toLowerCase(), "rest")) {
            System.out.println("Invalid communication protocol provided.");
            System.out.println("Available communication protocols: 'soap' | 'rest'");
            System.out.println("Example usage: java Main.java <communication_protocol>");
            System.exit(1);
        }

        if (Objects.equals(args[1].toLowerCase(), "soap")) {
            clientProtocol = CommunicationProtocol.SOAP;
        } else {
            clientProtocol = CommunicationProtocol.REST;
        }

        // TODO: present the client protocol being used somewhere on the app.
        // This helps the user know what protocol the client is currently using.

        // TODO: frontend server (tomcat) connection details should provided using arguments

        launch();
    }

    public static Stage getRootStage() {
        return rootStage;
    }

    public static UserSession getUserSession() {
        return session;
    }
}