package cd.project.client.ui.components;

import cd.project.client.Main;
import cd.project.client.Router;
import cd.project.client.core.BookingServiceSoap;
import cd.project.client.core.UnauthorizedException;
import cd.project.frontend.soap.BookingService;
import jakarta.xml.ws.WebServiceException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MyBookingsLayout extends HBox {
    @FXML
    private VBox content;

    public MyBookingsLayout() {
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + ";");
        constructContent();
        getChildren().addAll(content);
    }

    private void constructContent() {
        content = new VBox();
        content.setId("content");
        content.setPadding(new Insets(40));
        content.setSpacing(20);

        Label title = new Label("My Bookings");
        title.setFont(Font.font(title.getFont().getName(), FontWeight.BOLD, 30));
        title.setTextFill(Color.valueOf(Main.TITLE_COLOR_PRIMARY));

        Label reservationLink = new Label("Some Title");
        reservationLink.setFont(Font.font(title.getFont().getName(), FontWeight.BOLD, 20));
        reservationLink.setTextFill(Color.valueOf(Main.TEXT_COLOR_PRIMARY));

        Button testing = new Button("TEST ME, DADDY!");
        testing.setOnAction(actionEvent -> {
            try {
                BookingServiceSoap.test();
            } catch (UnauthorizedException e) {
                Main.sessionExpiredNotification = true;
                Router.navigateToLogin();
            }
        });

        content.getChildren().addAll(title, reservationLink, testing);
    }
}
