package cd.project.client.ui.controllers;

import cd.project.backend.domain.Lounge;
import cd.project.client.Main;
import cd.project.client.core.BookingServiceSoap;
import cd.project.client.core.SoapUtilities;
import cd.project.client.core.UnauthorizedException;
import cd.project.client.ui.Styles;
import cd.project.client.ui.components.AppMenu;
import cd.project.client.ui.components.ProtocolLabel;
import cd.project.frontend.soap.entities.BookingSoap;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyBookingsController implements Initializable {
    @FXML
    private VBox container;

    private ArrayList<BookingSoap> userBookings = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VBox content = new VBox();
        content.setPadding(new Insets(30));
        content.setSpacing(20);

        Label title = new Label("My Bookings");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + ";");

        ScrollPane bookingListing = new ScrollPane();
        bookingListing.getStylesheets().add(Styles.getPath());
        bookingListing.setFitToWidth(true);
        VBox myBookingsContainer = new VBox();
        myBookingsContainer.setAlignment(Pos.CENTER);
        myBookingsContainer.setSpacing(30);

        // TODO: get user bookings and display them
        try {
            userBookings = BookingServiceSoap.getUserBookings();
        } catch (UnauthorizedException e) {
            SoapUtilities.handleExpiredSession();
        }

        for (BookingSoap userBooking : userBookings) {
            VBox bookingContainer = this.bookingContainer(
                    userBooking.getId(),
                    LocalDate.parse(userBooking.getDate()),
                    LocalTime.parse(userBooking.getFromTime()),
                    LocalTime.parse(userBooking.getToTime()),
                    LocalDateTime.parse(userBooking.getCreatedAt()),
                    userBooking.getLounges()
            );
            myBookingsContainer.getChildren().add(bookingContainer);
        }

        bookingListing.setContent(myBookingsContainer);

        content.getChildren().addAll(title, bookingListing);
        container.getChildren().addFirst(new AppMenu());
        container.getChildren().add(content);
        container.getChildren().addLast(new ProtocolLabel());
    }

    private VBox bookingContainer(
            int bookingId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            //int amoutOfPeople,
            LocalDateTime createdAt,
            ArrayList<Lounge> lounges
    ) {
        VBox container = new VBox();
        container.maxWidth(400);
        container.setPadding(new Insets(20));
        container.setSpacing(25);
        container.getStyleClass().add("booking-container");
        container.getStylesheets().add(Styles.getPath());

        // header section
        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(10);
        HBox filler = new HBox();
        HBox.setHgrow(filler, Priority.ALWAYS);
        HBox mainSection = new HBox();
        HBox.setHgrow(mainSection, Priority.ALWAYS);
        mainSection.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Booking #" + bookingId);
        title.getStyleClass().add("title");
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-button");

        // TODO: implement this
        cancelButton.setOnMouseClicked(mouseEvent -> {
            try {
                ArrayList<BookingSoap> temp = BookingServiceSoap.getUserBookings();
                System.out.println("--- TEMP ---");
                for (BookingSoap booking : temp) {
                    System.out.println(booking.getId());
                }
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }
        });

        Separator separator = new Separator();
        mainSection.getChildren().addAll(title, filler, cancelButton);
        header.getChildren().addAll(mainSection, separator);

        // details section
        HBox detailsContainer = new HBox();
        HBox.setHgrow(detailsContainer, Priority.ALWAYS);
        detailsContainer.setAlignment(Pos.CENTER_LEFT);
        detailsContainer.setSpacing(25);

        HBox dateSection = new HBox();
        dateSection.setAlignment(Pos.CENTER_LEFT);
        dateSection.setSpacing(10);
        Label dateLabel = new Label("Date");
        Label dateData = new Label(date.toString());
        dateData.setStyle("-fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-weight: normal;");
        dateSection.getChildren().addAll(dateLabel, dateData);

        HBox fromSection = new HBox();
        fromSection.setAlignment(Pos.CENTER_LEFT);
        fromSection.setSpacing(10);
        Label fromLabel = new Label("From");
        Label fromTimeLabel = new Label(fromTime.toString());
        fromTimeLabel.setStyle("-fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-weight: normal;");
        fromSection.getChildren().addAll(fromLabel, fromTimeLabel);

        HBox toSection = new HBox();
        toSection.setAlignment(Pos.CENTER_LEFT);
        toSection.setSpacing(10);
        Label toLabel = new Label("To");
        Label toTimeLabel = new Label(toTime.toString());
        toTimeLabel.setStyle("-fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-weight: normal;");
        toSection.getChildren().addAll(toLabel, toTimeLabel);

        // people section
        /*HBox peopleSection = new HBox();
        peopleSection.setAlignment(Pos.CENTER_LEFT);
        peopleSection.setSpacing(15);
        boolean isWindowsSystem = System.getProperty("os.name").toLowerCase().contains("windows");
        String pathPrefix = isWindowsSystem ? "file:/" : "file://";
        Image usersImage = new Image(pathPrefix + System.getProperty("user.dir") + "/client/src/main/resources/cd/project/client/assets/users_icon.png");
        ImageView usersIcon = new ImageView(usersImage);
        usersIcon.setPreserveRatio(true);
        usersIcon.setFitWidth(25);
        usersIcon.setFitHeight(25);
        Label peopleAmount = new Label(String.valueOf(amoutOfPeople));
        peopleAmount.setStyle("-fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-weight: normal;");
        peopleSection.getChildren().addAll(usersIcon, peopleAmount);*/

        detailsContainer.getChildren().addAll(dateSection, fromSection, toSection);

        // lounges section
        VBox loungesSection = this.bookingTableContainer(lounges);

        // created at section
        HBox creationAtSection = new HBox();
        HBox.setHgrow(creationAtSection, Priority.ALWAYS);
        creationAtSection.setAlignment(Pos.CENTER_LEFT);
        creationAtSection.setSpacing(10);
        Label createdAtLabel = new Label("Booked on");
        createdAtLabel.setStyle("-fx-font-weight: normal; -fx-font-size: 14px;");
        DateTimeFormatter createAtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String parsedCreatedAt = createdAt.format(createAtFormatter).replace(" ", " at ");
        Label creationTimestamp = new Label(parsedCreatedAt);
        creationTimestamp.setStyle("-fx-text-fill: " + Main.TITLE_COLOR_PRIMARY + "; -fx-font-weight: normal;" +
                "-fx-font-size: 14px;");
        creationAtSection.getChildren().addAll(createdAtLabel, creationTimestamp);

        container.getChildren().addAll(header, detailsContainer, loungesSection, creationAtSection);
        return container;
    }

    private VBox bookingTableContainer(ArrayList<Lounge> lounges) {
        VBox bookingTableContainer = new VBox();
        bookingTableContainer.setSpacing(15);
        bookingTableContainer.setAlignment(Pos.CENTER);

        int colWidth = 175;
        TableView<String[]> bookingTable = new TableView<>();
        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        bookingTable.getStylesheets().add(Styles.getPath());
        bookingTable.setMaxWidth(colWidth * 3);
        bookingTable.setMaxHeight(170);
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

        // table items
        ObservableList<String[]> tableData = FXCollections.observableArrayList();
        if (lounges != null) {
            for (Lounge lounge : lounges) {
                tableData.add(new String[]{
                        String.valueOf(lounge.getId()),
                        String.valueOf(lounge.getBeachId()),
                        String.valueOf(lounge.getMaxCapacity())
                });
            }
        }

        bookingTable.setItems(tableData);
        bookingTable.getColumns().addAll(loungeIdCol, beachCol, capacityCol);

        bookingTableContainer.getChildren().addAll(bookingTable);
        return bookingTableContainer;
    }

    private ArrayList<BookingSoap> getUserBookings() {
        return null;
    }
}
