package cd.project.client.ui.components;

import cd.project.client.Main;
import cd.project.client.Router;
import cd.project.client.core.UserSession;
import cd.project.client.ui.Styles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AppMenu extends HBox {
    public AppMenu() {
        // Components properties
        setMinHeight(45);
        setHgrow(this, Priority.ALWAYS);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 20, 10, 20));
        this.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + "; " +
                "-fx-border-width: 0 0 2px 0; -fx-border-color: #262839;");

        // Navigation Menu Items
        VBox logo = this.logo();

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(0, 0, 0, 30));
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setSpacing(10);

        // home
        Hyperlink home = new Hyperlink("Home");
        home.getStylesheets().add(Styles.getPath());
        home.setOnAction(actionEvent -> Router.navigateToHome());

        // my bookings
        Hyperlink myBookings = new Hyperlink("My Bookings");
        myBookings.getStylesheets().add(Styles.getPath());
        myBookings.setOnAction(actionEvent -> Router.navigateToMyBookings());

        // about
        Hyperlink about = new Hyperlink("About");
        about.getStylesheets().add(Styles.getPath());
        about.setOnAction(actionEvent -> Router.navigateToAbout());

        // new booking
        Button newBooking = new Button("Book");
        newBooking.getStylesheets().add(Styles.getPath());
        newBooking.getStyleClass().add("book-button");
        newBooking.setOnAction(actionEvent -> Router.navigateToNewBooking());

        HBox whitespace = this.whitespace();
        HBox userDetails = this.userDetails();

        // TODO: implement navigation items availability based on user being authenticated
        if (UserSession.isAuthenticated()) {
            buttons.getChildren().addAll(myBookings, about, newBooking);
            this.getChildren().addAll(logo, buttons, whitespace, userDetails);
        } else {
            buttons.getChildren().addAll(home, about);
            this.getChildren().addAll(logo, buttons, whitespace);
        }
    }

    private VBox logo() {
        VBox logoContainer = new VBox();
        logoContainer.setAlignment(Pos.CENTER);
        Label topText = new Label("OceanView");
        Label botText = new Label("Seats");
        String logoColor = "#31344C";
        topText.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: " + logoColor + ";");
        botText.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: " + logoColor + ";");
        logoContainer.getChildren().addAll(topText, botText);
        return logoContainer;
    }

    private HBox whitespace() {
        HBox whitespace = new HBox();
        HBox.setHgrow(whitespace, Priority.ALWAYS);
        return whitespace;
    }

    private HBox userDetails() {
        // assets path prefix varies by OS
        boolean isWindowsSystem = System.getProperty("os.name").toLowerCase().contains("windows");
        String pathPrefix = isWindowsSystem ? "file:/" : "file://";

        HBox userDetails = new HBox();
        HBox.setHgrow(userDetails, Priority.ALWAYS);
        userDetails.setAlignment(Pos.CENTER_RIGHT);
        userDetails.setSpacing(15);
        Label username = new Label(UserSession.getUsername());
        username.setStyle("-fx-font-size: 15px; -fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + " ;");
        Image userImage = new Image(pathPrefix + System.getProperty("user.dir") + "/client/src/main/resources/cd/project/client/assets/user_icon.png");
        ImageView userIcon = new ImageView(userImage);
        userIcon.setPreserveRatio(true);
        userIcon.setFitWidth(32);
        userIcon.setFitHeight(32);

        // logout icon
        Image logoutImageGrey = new Image(
                pathPrefix + System.getProperty("user.dir") +
                "/client/src/main/resources/cd/project/client/assets/logout_icon_grey.png"
        );
        Image logoutImageBlue = new Image(
                pathPrefix + System.getProperty("user.dir") +
                "/client/src/main/resources/cd/project/client/assets/logout_icon_blue.png"
        );
        ImageView logoutIcon = new ImageView(logoutImageGrey);
        logoutIcon.setPreserveRatio(true);
        logoutIcon.setFitWidth(25);
        logoutIcon.setFitHeight(25);
        StackPane imageContainer = new StackPane(logoutIcon);
        imageContainer.setStyle("-fx-cursor: hand;");

        imageContainer.setOnMouseEntered(e -> logoutIcon.setImage(logoutImageBlue));
        imageContainer.setOnMouseExited(e -> logoutIcon.setImage(logoutImageGrey));

        imageContainer.setOnMouseClicked(mouseEvent -> {
            UserSession.invalidateSession();
            Router.navigateToHome();
        });

        userDetails.getChildren().addAll(username, userIcon, imageContainer);
        return userDetails;
    }
}
