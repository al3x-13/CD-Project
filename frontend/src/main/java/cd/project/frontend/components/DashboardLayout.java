package cd.project.frontend.components;

import cd.project.frontend.Main;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class DashboardLayout extends HBox {
    @FXML
    private VBox drawer;
    @FXML
    private VBox content;

    public DashboardLayout() {
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + ";");
        constructDrawer();
        constructContent();
        getChildren().addAll(drawer, content);
    }

    private void constructDrawer() {
        // component properties
        drawer = new VBox();
        drawer.setId("drawer");
        drawer.setAlignment(Pos.TOP_CENTER);
        drawer.minHeightProperty().bind(Bindings.selectDouble(Main.getRootStage().heightProperty()));
        drawer.setMinWidth(150);
        drawer.setPrefWidth(200);
        drawer.maxHeightProperty().bind(Bindings.selectDouble(Main.getRootStage().widthProperty()).multiply(0.25));
        VBox.setVgrow(drawer, Priority.ALWAYS);
        drawer.setFillWidth(true);

        // component styles
        drawer.setStyle(
            "-fx-background-color: " + Main.DRAWER_BACKGROUND_COLOR + ";"
        );

        // layout
        Label appTitle = new Label("OceanView Seats");
        appTitle.setPadding(new Insets(25, 0, 30, 0));  // t r b l
        appTitle.setFont(Font.font(appTitle.getFont().getName(), FontWeight.BOLD, 24));
        appTitle.setTextAlignment(TextAlignment.CENTER);
        appTitle.setWrapText(true);
        appTitle.setTextFill(Color.valueOf(Main.TITLE_COLOR_PRIMARY));

        Hyperlink myReservations = new Hyperlink("My Reservations");
        myReservations.setPadding(new Insets(10));
        myReservations.setFont(Font.font(myReservations.getFont().getName(), FontWeight.BOLD, 16));
        myReservations.setTextFill(Color.valueOf(Main.DRAWER_TEXT_COLOR));
        myReservations.setOnMouseEntered(mouseEvent -> {
            myReservations.setStyle("-fx-underline: false; -fx-focus-color: transparent;");
            myReservations.setTextFill(Color.valueOf(Main.TITLE_COLOR_PRIMARY));
        });
        myReservations.setOnMouseExited(mouseEvent -> {
            myReservations.setTextFill(Color.valueOf(Main.DRAWER_TEXT_COLOR));
        });
        myReservations.setOnAction(actionEvent -> System.out.println("TODO"));

        // adds elements
        drawer.getChildren().addAll(appTitle, myReservations);
    }

    private void constructContent() {
        content = new VBox();
        content.setId("content");
        content.setPadding(new Insets(30));

        Label title = new Label("Dashboard");
        title.setFont(Font.font(title.getFont().getName(), FontWeight.BOLD, 30));
        title.setTextFill(Color.valueOf(Main.TITLE_COLOR_PRIMARY));

        Label reservationLink = new Label("My Reservations");
        reservationLink.setPadding(new Insets(30, 0, 0, 0));
        reservationLink.setFont(Font.font(title.getFont().getName(), FontWeight.BOLD, 20));
        reservationLink.setTextFill(Color.valueOf(Main.TEXT_COLOR_PRIMARY));

        content.getChildren().addAll(title, reservationLink);
    }
}
