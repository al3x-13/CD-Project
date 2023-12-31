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
    public static String BACKGROUND_COLOR = "#181920";
    public static String BACKGROUND_COLOR_2 = "#31344C";
    public static String DRAWER_BACKGROUND_COLOR = "#35383a";
    public static String DRAWER_TEXT_COLOR = "#b8baba";
    public static String TITLE_COLOR_PRIMARY = "#2988c9";
    public static String TEXT_COLOR_PRIMARY = "#5e6d88";

    // User session
    private static final UserSession session = new UserSession();
    public static String serverAddress;
    public static int serverPort;
    public static CommunicationProtocol clientProtocol;
    public static boolean sessionExpiredNotification = false;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        rootStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scenes/home.fxml"));
        Parent root = fxmlLoader.load();

        // Initial scene setup
        Scene home = new Scene(root, 1000, 750);
        stage.setTitle("CD");
        stage.setScene(home);
        stage.show();
    }

    public static void main(String[] args) {
        parseApplicationArguments(args);
        launch();
    }

    public static Stage getRootStage() {
        return rootStage;
    }

    public static UserSession getUserSession() {
        return session;
    }

    private static void parseApplicationArguments(String[] args) {
        if (args.length < 3) {
            System.out.println("Missing arguments");
            System.out.println("Example usage: java Main.java <server_address> <server_port> <communication_protocol>");
            System.exit(1);
        }

        // args
        serverAddress = args[0];
        String port = args[1];
        String communicationProtocol = args[2].toLowerCase();

        // server port validation
        try {
            serverPort = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            System.out.println("Invalid server port provided. Must be an integer");
            System.out.println("Example usage: java Main.java <server_address> <server_port> <communication_protocol>");
            System.exit(1);
        }

        // comm protocol validation
        if (!Objects.equals(communicationProtocol, "soap") && !Objects.equals(communicationProtocol, "rest")) {
            System.out.println("Invalid communication protocol provided.");
            System.out.println("Available communication protocols: 'soap' | 'rest'");
            System.out.println("Example usage: java Main.java <server_address> <server_port> <communication_protocol>");
            System.exit(1);
        }

        if (Objects.equals(communicationProtocol, "soap")) {
            clientProtocol = CommunicationProtocol.SOAP;
        } else {
            clientProtocol = CommunicationProtocol.REST;
        }
    }
}