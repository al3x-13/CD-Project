package cd.project.client.ui.controllers;

import cd.project.client.Main;
import cd.project.client.ui.components.AppMenu;
import cd.project.client.ui.components.ProtocolLabel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class NewBookingController implements Initializable {
    private final String STYLES_PATH = this.getStylesPath();

    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox content = new VBox();
        content.setPadding(new Insets(25));
        content.setSpacing(24);

        HBox titleContainer = this.titleContainer();

        // TODO: add handlers for when the values in the inputs change
        // TODO: store them in class variables (??)
        HBox row1 = new HBox();
        HBox.setHgrow(row1, Priority.ALWAYS);
        row1.setAlignment(Pos.CENTER_LEFT);
        row1.setSpacing(60);
        HBox beachInputContainer = this.beachInputContainer();
        HBox peopleInputContainer = this.peopleInputContainer();
        row1.getChildren().addAll(beachInputContainer, peopleInputContainer);

        HBox row2 = new HBox();
        HBox.setHgrow(row2, Priority.ALWAYS);
        row2.setAlignment(Pos.CENTER_LEFT);
        row2.setSpacing(40);
        HBox dateInputContainer = this.dateInputContainer();
        HBox fromTimeInputContainer = this.fromTimeInputContainer();
        HBox toTimeInputContainer = this.toTimeInputContainer();
        row2.getChildren().addAll(dateInputContainer, fromTimeInputContainer, toTimeInputContainer);

        HBox row3 = new HBox();
        HBox.setHgrow(row3, Priority.ALWAYS);
        row3.setAlignment(Pos.CENTER_LEFT);
        row3.setSpacing(40);
        VBox availabilityCheckContainer = this.availabilityCheckContainer();
        row3.getChildren().addAll(availabilityCheckContainer);

        HBox row4 = new HBox();
        HBox.setHgrow(row4, Priority.ALWAYS);
        row4.setAlignment(Pos.CENTER_LEFT);
        row4.setSpacing(40);
        VBox bookingTable = this.bookingTableContainer();
        row4.getChildren().addAll(bookingTable);

        HBox row5 = new HBox();
        HBox.setHgrow(row5, Priority.ALWAYS);
        row5.setAlignment(Pos.CENTER_LEFT);
        row5.setSpacing(40);
        HBox submitBookingContainer = this.submitBookingContainer();
        row5.getChildren().addAll(submitBookingContainer);

        // add content items
        content.getChildren().addAll(titleContainer, row1, row2, row3, row4, row5);

        // add layout items
        container.getChildren().addFirst(new AppMenu());
        container.getChildren().addAll(content);
        container.getChildren().addLast(new ProtocolLabel());
    }

    private String getStylesPath() {
        String projectDir = System.getProperty("user.dir");
        String cssFilePath = projectDir + "/client/src/main/resources/cd/project/client/css/styles.css";
        File cssFile = new File(cssFilePath);
        try {
            return cssFile.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private HBox titleContainer() {
        HBox container = new HBox();
        Label title = new Label("New Booking");
        title.setStyle("-fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-size: 24px; -fx-font-weight: bold;");
        container.getChildren().add(title);
        return container;
    }

    private HBox beachInputContainer() {
        HBox beachInputContainer = new HBox();
        beachInputContainer.setSpacing(15);
        beachInputContainer.setAlignment(Pos.CENTER_LEFT);
        Label beachLabel = new Label("Beach");
        beachLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");
        ChoiceBox<Character> beachInput = new ChoiceBox<>();
        beachInput.getItems().addAll('A', 'B', 'C');
        beachInput.setValue('A');
        beachInput.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR_2 + "; -fx-border-color: " +
                Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-border-width: 2; -fx-font-size: 15px; " +
                "-fx-text-fill: white; -fx-background-radius: 8;");
        beachInput.getStylesheets().add(this.STYLES_PATH);
        beachInputContainer.getChildren().addAll(beachLabel, beachInput);
        return beachInputContainer;
    }

    private HBox peopleInputContainer() {
        HBox peopleInputContainer = new HBox();
        peopleInputContainer.setSpacing(15);
        peopleInputContainer.setAlignment(Pos.CENTER_LEFT);

        Label peopleLabel = new Label("Amount of people");
        peopleLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        ChoiceBox<Integer> peopleInput = new ChoiceBox<>();
        for (int i = 1; i <= 15; i++) {
            peopleInput.getItems().add(i);
        }
        peopleInput.setValue(1);
        peopleInput.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR_2 + "; -fx-border-color: " +
                Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-border-width: 2; -fx-font-size: 15px; " +
                "-fx-text-fill: white; -fx-background-radius: 8;");
        peopleInput.getStylesheets().add(this.STYLES_PATH);
        peopleInputContainer.getChildren().addAll(peopleLabel, peopleInput);
        return peopleInputContainer;
    }

    private HBox dateInputContainer() {
        HBox dateInputContainer = new HBox();
        dateInputContainer.setSpacing(15);
        dateInputContainer.setAlignment(Pos.CENTER_LEFT);

        Label dateLabel = new Label("Date");
        dateLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        DatePicker dateInput = new DatePicker(LocalDate.now());
        // TODO: handle input change
        dateInput.getStylesheets().add(this.STYLES_PATH);

        dateInputContainer.getChildren().addAll(dateLabel, dateInput);
        return dateInputContainer;
    }

    private HBox fromTimeInputContainer() {
        HBox fromTimeInputContainer = new HBox();
        fromTimeInputContainer.setSpacing(15);
        fromTimeInputContainer.setAlignment(Pos.CENTER_LEFT);

        Label fromTimeLabel = new Label("From");
        fromTimeLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        ChoiceBox<String> fromTimeInput = new ChoiceBox<>();
        for (int i = 8; i <= 20; i++) {
            String data = i < 10 ? "0" + i + ":00" : i + ":00";
            fromTimeInput.getItems().add(data);
        }
        fromTimeInput.setValue("08:00");
        fromTimeInput.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR_2 + "; -fx-border-color: " +
                Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-border-width: 2; -fx-font-size: 15px; " +
                "-fx-text-fill: white; -fx-background-radius: 8;");
        fromTimeInput.getStylesheets().add(this.STYLES_PATH);

        fromTimeInputContainer.getChildren().addAll(fromTimeLabel, fromTimeInput);
        return fromTimeInputContainer;
    }

    private HBox toTimeInputContainer() {
        HBox toTimeInputContainer = new HBox();
        toTimeInputContainer.setSpacing(15);
        toTimeInputContainer.setAlignment(Pos.CENTER_LEFT);

        Label toTimeLabel = new Label("From");
        toTimeLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        ChoiceBox<String> toTimeInput = new ChoiceBox<>();
        for (int i = 8; i <= 20; i++) {
            String data = i < 10 ? "0" + i + ":00" : i + ":00";
            toTimeInput.getItems().add(data);
        }
        toTimeInput.setValue("08:00");
        toTimeInput.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR_2 + "; -fx-border-color: " +
                Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-border-width: 2; -fx-font-size: 15px; " +
                "-fx-text-fill: white; -fx-background-radius: 8;");
        toTimeInput.getStylesheets().add(this.STYLES_PATH);

        // TODO: only allow for values starting after the from time

        toTimeInputContainer.getChildren().addAll(toTimeLabel, toTimeInput);
        return toTimeInputContainer;
    }

    private VBox availabilityCheckContainer() {
        VBox availabilityCheckContainer = new VBox();
        availabilityCheckContainer.setSpacing(15);
        availabilityCheckContainer.setAlignment(Pos.CENTER_LEFT);

        Label checkAvailabilityLabel = new Label("Check availability");
        checkAvailabilityLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        HBox checkButtonContainer = new HBox();
        checkButtonContainer.setSpacing(15);
        checkButtonContainer.setAlignment(Pos.CENTER_LEFT);

        Button checkAvailabilityButton = new Button("Check availability");
        checkAvailabilityButton.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR_2 + "; -fx-text-fill: " +
                 Main.TITLE_COLOR_PRIMARY + "; -fx-font-size: 16px;");
        Label checkStatus = this.checkStatus();

        checkButtonContainer.getChildren().addAll(checkAvailabilityButton, checkStatus);

        availabilityCheckContainer.getChildren().addAll(checkAvailabilityLabel, checkButtonContainer);
        return availabilityCheckContainer;
    }

    private Label checkStatus() {
        Label checkStatus = new Label("Checking availability...");
        checkStatus.setStyle("-fx-font-size: 16px; -fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + ";");
        return checkStatus;
    }

    private VBox bookingTableContainer() {
        VBox bookingTableContainer = new VBox();
        bookingTableContainer.setSpacing(15);
        bookingTableContainer.setAlignment(Pos.CENTER_LEFT);

        Label tableLabel = new Label("Booking lounges");
        tableLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<String[]> bookingTable = new TableView<>();
        ObservableList<String[]> bookingTableData = FXCollections.observableArrayList();
        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        bookingTable.getStylesheets().add(this.STYLES_PATH);
        bookingTable.setMaxWidth(500);
        bookingTable.setMaxHeight(180);
        bookingTable.setEditable(false);
        int colWidth = 150;

        TableColumn<String[], String> loungeIdCol = new TableColumn<>("Lounge ID");
        loungeIdCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()[0]));
        loungeIdCol.setPrefWidth(colWidth);
        loungeIdCol.setResizable(false);

        TableColumn<String[], String> beachCol = new TableColumn<>("Beach");
        beachCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()[1]));
        beachCol.setPrefWidth(colWidth);
        beachCol.setResizable(false);
        beachCol.setSortable(false);

        TableColumn<String[], String> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()[2]));
        capacityCol.setPrefWidth(colWidth);
        capacityCol.setResizable(false);
        capacityCol.setSortable(false);

        bookingTable.getColumns().addAll(loungeIdCol, beachCol, capacityCol);

        // TODO: remove this
        bookingTableData.add(new String[]{ "test1", "test2", "test3" });
        bookingTableData.add(new String[]{ "test4", "test5", "test6" });
        bookingTable.setItems(bookingTableData);

        bookingTableContainer.getChildren().addAll(tableLabel, bookingTable);
        return bookingTableContainer;
    }

    private HBox submitBookingContainer() {
        HBox submitBookingContainer = new HBox();
        submitBookingContainer.setSpacing(15);
        submitBookingContainer.setAlignment(Pos.CENTER_LEFT);

        // TODO: only enabled if there are lounges in the table
        Button submitButton = new Button("Book");
        submitButton.setDisable(true);
        submitButton.setStyle("-fx-background-color: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-size: 17px; " +
                "-fx-text-fill: " + Main.BACKGROUND_COLOR_2 + "; -fx-font-weight: bold; -background-radius: 6;");

        submitBookingContainer.getChildren().addAll(submitButton);
        return submitBookingContainer;
    }
}