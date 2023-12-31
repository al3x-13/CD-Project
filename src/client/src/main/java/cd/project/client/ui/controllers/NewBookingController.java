package cd.project.client.ui.controllers;

import cd.project.backend.domain.Lounge;
import cd.project.client.Main;
import cd.project.client.Router;
import cd.project.client.core.BookingService;
import cd.project.client.ui.Styles;
import cd.project.client.ui.components.AppMenu;
import cd.project.client.ui.components.ProtocolLabel;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewBookingController implements Initializable {
    private final String STYLES_PATH = Styles.getPath();

    @FXML
    private VBox container;

    // components data
    private final StringProperty beachId = new SimpleStringProperty("");
    private final IntegerProperty amountOfPeople = new SimpleIntegerProperty(-1);
    private LocalDate date = null;
    private final IntegerProperty fromTime = new SimpleIntegerProperty(-1);
    private final IntegerProperty toTime = new SimpleIntegerProperty(-1);
    private final BooleanProperty checkAvailabilitySpinnerVisibility = new SimpleBooleanProperty(false);
    private final ObservableList<String[]> bookingsTableData = FXCollections.observableArrayList();
    private final BooleanProperty bookingButtonDisabled = new SimpleBooleanProperty(this.bookingsTableData.isEmpty());
    private final Label checkStatus = this.checkStatus();
    private final Label bookingSuccessLabel = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.setAlignment(Pos.CENTER);

        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(25));
        content.setSpacing(24);

        HBox titleContainer = this.titleContainer();

        HBox row1 = new HBox();
        HBox.setHgrow(row1, Priority.ALWAYS);
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(60);
        HBox beachInputContainer = this.beachInputContainer();
        HBox peopleInputContainer = this.peopleInputContainer();
        row1.getChildren().addAll(beachInputContainer, peopleInputContainer);

        HBox row2 = new HBox();
        HBox.setHgrow(row2, Priority.ALWAYS);
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(40);
        HBox dateInputContainer = this.dateInputContainer();
        HBox fromTimeInputContainer = this.fromTimeInputContainer();
        HBox toTimeInputContainer = this.toTimeInputContainer();
        row2.getChildren().addAll(dateInputContainer, fromTimeInputContainer, toTimeInputContainer);

        HBox row3 = new HBox();
        HBox.setHgrow(row3, Priority.ALWAYS);
        row3.setAlignment(Pos.CENTER);
        row3.setSpacing(40);
        VBox availabilityCheckContainer = this.availabilityCheckContainer();
        row3.getChildren().addAll(availabilityCheckContainer);

        HBox row4 = new HBox();
        HBox.setHgrow(row4, Priority.ALWAYS);
        row4.setAlignment(Pos.CENTER);
        row4.setSpacing(40);
        VBox bookingTable = this.bookingTableContainer();
        row4.getChildren().addAll(bookingTable);

        HBox row5 = new HBox();
        HBox.setHgrow(row5, Priority.ALWAYS);
        row5.setAlignment(Pos.CENTER);
        row5.setSpacing(40);
        VBox submitBookingContainer = this.submitBookingContainer();
        row5.getChildren().addAll(submitBookingContainer);

        // add content items
        content.getChildren().addAll(titleContainer, row1, row2, row3, row4, row5);

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
        beachInput.getStylesheets().add(this.STYLES_PATH);
        this.beachId.setValue("A");

        beachInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.beachId.setValue(newValue.toString());
            this.resetData();
        });

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
        peopleInput.getStylesheets().add(this.STYLES_PATH);
        this.amountOfPeople.setValue(1);

        peopleInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.amountOfPeople.setValue(newValue);
            this.resetData();
        });

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
        dateInput.getStylesheets().add(this.STYLES_PATH);
        this.date = LocalDate.now();

        dateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.date = newValue;
            this.resetData();
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
        fromTimeInput.getStylesheets().add(this.STYLES_PATH);

        fromTimeInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int value = Integer.parseInt(newValue.split(":")[0]);
                this.fromTime.setValue(value);
            }
            this.resetData();
        });

        fromTimeInputContainer.getChildren().addAll(fromTimeLabel, fromTimeInput);
        return fromTimeInputContainer;
    }

    private HBox toTimeInputContainer() {
        HBox toTimeInputContainer = new HBox();
        toTimeInputContainer.setSpacing(15);
        toTimeInputContainer.setAlignment(Pos.CENTER_LEFT);

        Label toTimeLabel = new Label("To");
        toTimeLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        ChoiceBox<String> toTimeInput = new ChoiceBox<>();
        for (int i = 9; i <= 20; i++) {
            String data = i < 10 ? "0" + i + ":00" : i + ":00";
            toTimeInput.getItems().add(data);
        }
        toTimeInput.setValue("09:00");
        this.toTime.setValue(9);
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
            this.resetData();
        });

        // update 'toTime' value on change
        toTimeInput.setOnAction(actionEvent -> {
        });

        toTimeInput.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                int value = Integer.parseInt(newValue.split(":")[0]);
                this.toTime.setValue(value);
            }
            this.resetData();
        }));

        toTimeInputContainer.getChildren().addAll(toTimeLabel, toTimeInput);
        return toTimeInputContainer;
    }

    private VBox availabilityCheckContainer() {
        VBox availabilityCheckContainer = new VBox();
        availabilityCheckContainer.setSpacing(15);
        availabilityCheckContainer.setAlignment(Pos.CENTER);

        HBox checkButtonContainer = new HBox();
        checkButtonContainer.setPadding(new Insets(20, 0, 0, 0));
        checkButtonContainer.setSpacing(15);
        checkButtonContainer.setAlignment(Pos.CENTER);

        Button checkAvailabilityButton = new Button("Check availability");
        checkAvailabilityButton.getStyleClass().add(this.STYLES_PATH);
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

        checkAvailabilityButton.setOnAction(actionEvent -> {
            checkStatus.setText("Checking availability...");
            this.checkAvailabilitySpinnerVisibility.setValue(true);

            // get available lounges
            ArrayList<Lounge> availableLounges = BookingService.checkBookingAvailability(
                    this.beachId.get().charAt(0),
                    this.date,
                    LocalTime.of(this.fromTime.intValue(), 0),
                    LocalTime.of(this.toTime.intValue(), 0),
                    this.amountOfPeople.intValue()
            );
            this.fillTable(availableLounges);

            if (availableLounges == null) {
                checkStatus.setText("No availability");
                checkStatus.setStyle("-fx-font-size: 15px; -fx-text-fill: #BE1F1F;");
            } else {
                checkStatus.setText("Available");
                checkStatus.setStyle("-fx-font-size: 15px; -fx-text-fill: #0AB613;");
            }
        });

        checkButtonContainer.getChildren().addAll(checkAvailabilityButton);
        availabilityCheckContainer.getChildren().addAll(checkButtonContainer, checkStatus);
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

        Label tableLabel = new Label("Booking lounges");
        tableLabel.setStyle("-fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; -fx-font-size: 18px; -fx-font-weight: bold;");

        int colWidth = 175;
        TableView<String[]> bookingTable = new TableView<>();
        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        bookingTable.getStylesheets().add(this.STYLES_PATH);
        bookingTable.setMaxWidth(colWidth * 3);
        bookingTable.setMaxHeight(180);
        bookingTable.setEditable(false);

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

    private VBox submitBookingContainer() {
        VBox submitBookingContainer = new VBox();
        submitBookingContainer.setSpacing(15);
        submitBookingContainer.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Book");
        submitButton.getStyleClass().add(this.STYLES_PATH);
        submitButton.setStyle("-fx-background-color: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-size: 17px; " +
                "-fx-text-fill: " + Main.BACKGROUND_COLOR_2 + "; -fx-font-weight: bold; -fx-background-radius: 6; " +
                "-fx-cursor: hand;");

        // hover styles
        submitButton.setOnMouseEntered(mouseEvent ->
                submitButton.setStyle("-fx-background-color: #1F72AB; -fx-font-size: 17px; " +
                        "-fx-text-fill: " + Main.BACKGROUND_COLOR_2 + "; -fx-font-weight: bold; -fx-background-radius: 6; " +
                        "-fx-cursor: hand;"
                )
        );
        submitButton.setOnMouseExited(mouseEvent ->
                submitButton.setStyle("-fx-background-color: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-size: 17px; " +
                        "-fx-text-fill: " + Main.BACKGROUND_COLOR_2 + "; -fx-font-weight: bold; -fx-background-radius: 6; " +
                        "-fx-cursor: hand;"
                )
        );

        // only enabled if there are lounges in the table
        submitButton.disableProperty().bind(this.bookingButtonDisabled);

        // TODO: implement this
        submitButton.setOnAction(actionEvent -> {
            int bookingId = BookingService.createBooking(
                    this.beachId.get().charAt(0),
                    this.date,
                    LocalTime.of(this.fromTime.intValue(), 0),
                    LocalTime.of(this.toTime.intValue(), 0),
                    this.amountOfPeople.intValue()
            );

            if (bookingId != -1) {      // SUCCESS
                this.bookingSuccessLabel.setText("Booked successfully");
                this.bookingSuccessLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #0AB613;");

                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished(actionEvent1 -> Router.navigateToMyBookings());
                delay.play();
            } else {
                this.bookingSuccessLabel.setText("Failed to book lounges");
                this.bookingSuccessLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #BE1F1F;");
            }
        });

        submitBookingContainer.getChildren().addAll(submitButton, this.bookingSuccessLabel);
        return submitBookingContainer;
    }

    private void fillTable(ArrayList<Lounge> lounges) {
        if (lounges == null) return;

        for (Lounge lounge : lounges) {
            this.bookingsTableData.add(new String[]{
                    lounge.getId(),
                    String.valueOf(lounge.getBeachId()),
                    String.valueOf(lounge.getMaxCapacity())
            });
        }
    }

    private void resetData() {
        this.bookingsTableData.clear();
        this.checkStatus.setText("");
        this.checkStatus.setStyle("-fx-font-size: 15px; -fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + ";");
        this.bookingSuccessLabel.setText("");
    }
}
