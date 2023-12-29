package cd.project.client.ui.controllers;

import cd.project.backend.domain.Lounge;
import cd.project.client.Main;
import cd.project.client.core.BookingServiceSoap;
import cd.project.client.core.SoapUtilities;
import cd.project.client.core.UnauthorizedException;
import cd.project.client.ui.Styles;
import cd.project.client.ui.components.AppMenu;
import cd.project.client.ui.components.ProtocolLabel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AvailableLoungesController implements Initializable {
    private final String STYLES_PATH = Styles.getPath();

    @FXML
    private VBox container;

    // components data
    private StringProperty beachId = new SimpleStringProperty("A");
    private LocalDate date = null;
    private IntegerProperty fromTime = new SimpleIntegerProperty(-1);
    private IntegerProperty toTime = new SimpleIntegerProperty(-1);
    private BooleanProperty checkAvailabilitySpinnerVisibility = new SimpleBooleanProperty(false);
    private ObservableList<String[]> bookingsTableData = FXCollections.observableArrayList();
    private BooleanProperty bookingButtonDisabled = new SimpleBooleanProperty(this.bookingsTableData.isEmpty());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox content = new VBox();
        content.setPadding(new Insets(25));
        content.setSpacing(24);

        HBox titleContainer = this.titleContainer();

        // TODO: add handlers for when the values in the inputs change
        HBox row1 = new HBox();
        HBox.setHgrow(row1, Priority.ALWAYS);
        row1.setAlignment(Pos.CENTER_LEFT);
        row1.setSpacing(60);
        HBox beachInputContainer = this.beachInputContainer();
        row1.getChildren().addAll(beachInputContainer);

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

        // add content items
        content.getChildren().addAll(titleContainer, row1, row2, row3, row4);

        // add layout items
        container.getChildren().addFirst(new AppMenu());
        container.getChildren().addAll(content);
        container.getChildren().addLast(new ProtocolLabel());


        // global listeners
        this.bookingsTableData.addListener((ListChangeListener<? super String[]>) change -> {
            this.bookingButtonDisabled.setValue(this.bookingsTableData.isEmpty());
        });
    }

    private HBox titleContainer() {
        HBox container = new HBox();
        Label title = new Label("List Available Lounges");
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
        beachInput.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + "; -fx-border-color: " +
                Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-border-width: 1; -fx-font-size: 14px; " +
                "-fx-text-fill: white; -fx-background-radius: 8;");
        beachInput.getStylesheets().add(this.STYLES_PATH);
        this.beachId.setValue("A");

        beachInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.beachId.setValue(newValue.toString());
        });

        beachInputContainer.getChildren().addAll(beachLabel, beachInput);
        return beachInputContainer;
    }

    private HBox dateInputContainer() {
        HBox dateInputContainer = new HBox();
        dateInputContainer.setSpacing(15);
        dateInputContainer.setAlignment(Pos.CENTER_LEFT);

        Label dateLabel = new Label("Date");
        dateLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        DatePicker dateInput = new DatePicker(LocalDate.now());
        dateInput.getStylesheets().add(this.STYLES_PATH);
        this.date = LocalDate.now();

        dateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.date = newValue;
        });

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
        for (int i = 8; i <= 19; i++) {
            String data = i < 10 ? "0" + i + ":00" : i + ":00";
            fromTimeInput.getItems().add(data);
        }
        fromTimeInput.setValue("08:00");
        this.fromTime.setValue(8);
        fromTimeInput.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + "; -fx-border-color: " +
                Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-border-width: 1; -fx-font-size: 14px; " +
                "-fx-text-fill: white; -fx-background-radius: 8;");
        fromTimeInput.getStylesheets().add(this.STYLES_PATH);

        fromTimeInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int value = Integer.parseInt(newValue.split(":")[0]);
                this.fromTime.setValue(value);
            }
        });

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
        for (int i = 9; i <= 20; i++) {
            String data = i < 10 ? "0" + i + ":00" : i + ":00";
            toTimeInput.getItems().add(data);
        }
        toTimeInput.setValue("09:00");
        this.toTime.setValue(9);
        toTimeInput.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + "; -fx-border-color: " +
                Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-border-width: 1; -fx-font-size: 14px; " +
                "-fx-text-fill: white; -fx-background-radius: 8;");
        toTimeInput.getStylesheets().add(this.STYLES_PATH);

        // updates values when 'fromTime' changes
        this.fromTime.addListener((observable, oldValue, newValue) -> {
            int newToTimeValue = this.toTime.getValue();
            if (this.toTime.getValue() <= newValue.intValue()) {
                newToTimeValue = newValue.intValue() + 1;
            }

            toTimeInput.getItems().clear();
            for (int i = newValue.intValue() + 1; i <= 20; i++) {
                String data = i < 10 ? "0" + i + ":00" : i + ":00";
                toTimeInput.getItems().add(data);
            }

            toTimeInput.setValue(newToTimeValue < 10 ? "0" + newToTimeValue + ":00" : newToTimeValue + ":00");
        });

        // update 'toTime' value on change
        toTimeInput.setOnAction(actionEvent -> {
        });

        toTimeInput.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                int value = Integer.parseInt(newValue.split(":")[0]);
                this.toTime.setValue(value);
            }
        }));

        toTimeInputContainer.getChildren().addAll(toTimeLabel, toTimeInput);
        return toTimeInputContainer;
    }

    private VBox availabilityCheckContainer() {
        VBox availabilityCheckContainer = new VBox();
        availabilityCheckContainer.setSpacing(15);
        availabilityCheckContainer.setAlignment(Pos.CENTER_LEFT);

        Label checkAvailabilityLabel = new Label("Get available lounges");
        checkAvailabilityLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        HBox checkButtonContainer = new HBox();
        checkButtonContainer.setSpacing(15);
        checkButtonContainer.setAlignment(Pos.CENTER_LEFT);

        Button checkAvailabilityButton = new Button("Get lounges");
        checkAvailabilityButton.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + "; -fx-text-fill: " +
                 Main.TITLE_COLOR_PRIMARY + "; -fx-background-radius: 6; -fx-font-size: 14px; -fx-padding: 6px 12px; " +
                "-fx-border-color: " + Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-cursor: hand;");

        // hover styles
        checkAvailabilityButton.setOnMouseEntered(mouseEvent ->
                checkAvailabilityButton.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR_2 + "; -fx-text-fill: " +
                        Main.TITLE_COLOR_PRIMARY + "; -fx-background-radius: 6; -fx-font-size: 14px; -fx-padding: 6px 12px; " +
                        "-fx-border-color: " + Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-cursor: hand;"
                )
        );
        checkAvailabilityButton.setOnMouseExited(mouseEvent ->
                checkAvailabilityButton.setStyle("-fx-background-color: " + Main.BACKGROUND_COLOR + "; -fx-text-fill: " +
                        Main.TITLE_COLOR_PRIMARY + "; -fx-background-radius: 6; -fx-font-size: 14px; -fx-padding: 6px 12px; " +
                        "-fx-border-color: " + Main.TITLE_COLOR_PRIMARY + "; -fx-border-radius: 6; -fx-cursor: hand;"
                )
        );

        checkAvailabilityButton.getStyleClass().add(this.STYLES_PATH);

        Label checkStatus = this.checkStatus();
        ProgressIndicator checkLoadingSpinner = this.checkLoadingSpinner();

        checkAvailabilityButton.setOnMouseClicked(actionEvent -> {
            this.bookingsTableData.clear();
            checkStatus.setText("Getting available lounges...");
            this.checkAvailabilitySpinnerVisibility.setValue(true);
            ArrayList<Lounge> availableLounges = null;

            try {
                availableLounges = BookingServiceSoap.getAvailableLounges(
                        this.beachId.getValue().charAt(0),
                        this.date,
                        LocalTime.of(this.fromTime.intValue(), 0),
                        LocalTime.of(this.toTime.intValue(), 0)
                );

                if (availableLounges != null) {
                    for (Lounge availableLounge : availableLounges) {
                        this.bookingsTableData.add(new String[] {
                                availableLounge.getId(),
                                String.valueOf(availableLounge.getBeachId()),
                                String.valueOf(availableLounge.getMaxCapacity())
                        });
                    }
                }
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }

            this.checkAvailabilitySpinnerVisibility.setValue(false);
            if (availableLounges == null) {
                checkStatus.setText("No lounges available");
            } else {
                checkStatus.setText("");
            }
        });


        checkButtonContainer.getChildren().addAll(checkAvailabilityButton, checkStatus, checkLoadingSpinner);

        availabilityCheckContainer.getChildren().addAll(checkAvailabilityLabel, checkButtonContainer);
        return availabilityCheckContainer;
    }

    private Label checkStatus() {
        Label checkStatus = new Label();
        checkStatus.setStyle("-fx-font-size: 15px; -fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + ";");
        return checkStatus;
    }

    private ProgressIndicator checkLoadingSpinner() {
        ProgressIndicator loadingSpinner = new ProgressIndicator();
        loadingSpinner.setMaxWidth(25);
        loadingSpinner.setMaxHeight(25);
        loadingSpinner.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        loadingSpinner.visibleProperty().bind(this.checkAvailabilitySpinnerVisibility);
        return loadingSpinner;
    }

    private VBox bookingTableContainer() {
        VBox bookingTableContainer = new VBox();
        bookingTableContainer.setSpacing(15);
        bookingTableContainer.setAlignment(Pos.CENTER_LEFT);

        Label tableLabel = new Label("Available lounges");
        tableLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<String[]> bookingTable = new TableView<>();
        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        bookingTable.getStylesheets().add(this.STYLES_PATH);
        bookingTable.setMaxWidth(500);
        bookingTable.setMaxHeight(250);
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
        bookingTable.setItems(this.bookingsTableData);
        bookingTable.getColumns().addAll(loungeIdCol, beachCol, capacityCol);

        bookingTableContainer.getChildren().addAll(tableLabel, bookingTable);
        return bookingTableContainer;
    }
}
