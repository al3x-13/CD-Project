package cd.project.backend.domain;

import cd.project.backend.database.DbConnection;
import jakarta.xml.bind.annotation.XmlType;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@XmlType(name = "Booking")
public class Booking {
    private int id;
    private char beachID;
    private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private LocalDateTime createdAt;
    private int userID;
    private ArrayList<Lounge> lounges;

    public Booking() {}

    /**
     * Creates a new lounge booking.
     * @param beachID beach identifier
     * @param date booking date
     * @param fromTime start time
     * @param toTime end time
     * @param userID user identifier
     */
    public Booking(
            char beachID,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int userID,
            ArrayList<Lounge> lounges
    ){
        this.beachID = beachID;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.userID = userID;
        this.lounges = lounges;
    }

    /**
     * Creates a new lounge booking with id.
     * @param id booking id
     * @param beachID beach identifier
     * @param date booking date
     * @param fromTime start time
     * @param toTime end time
     * @param userID user identifier
     */
    public Booking(
            int id,
            char beachID,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            LocalDateTime createdAt,
            int userID,
            ArrayList<Lounge> lounges
    ){
        this.id = id;
        this.beachID = beachID;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.createdAt = createdAt;
        this.userID = userID;
        this.lounges = lounges;
    }

    /**
     * Creates a new lounge booking object from an existing database booking.
     * @param bookingID booking identifier
     */
    public Booking(int bookingID) throws Exception {
        this.id = bookingID;

        ResultSet data = DbConnection.executeQuery("SELECT * FROM bookings WHERE id = ?", bookingID);
        if (!data.next()) throw new Exception("Failed to instantiate object: booking id not found");

        this.id = data.getInt("id");
        this.beachID = data.getString("beach_id").charAt(0);
        this.date = data.getDate("date").toLocalDate();
        this.fromTime = data.getTime("from_time").toLocalTime();
        this.toTime = data.getTime("to_time").toLocalTime();
        this.createdAt = data.getTimestamp("created_at").toLocalDateTime();
        this.userID = data.getInt("user_id");
    }

    public int getId() {
        return this.id;
    }

    public char getBeachID() {
        return this.beachID;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getFromTime() {
        return this.fromTime;
    }

    public LocalTime getToTime() {
        return this.toTime;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public int getUserID() {
        return this.userID;
    }

    public ArrayList<Lounge> getLounges() {
        return this.lounges;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBeachID(char beachID) {
        this.beachID = beachID;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLounges(ArrayList<Lounge> lounges) {
        this.lounges = lounges;
    }

    private ArrayList<String> getLoungeIds() {
        ArrayList<String> ids = new ArrayList<>();
        for (Lounge lounge : this.lounges) {
            ids.add(lounge.getId());
        }
        return ids;
    }

    /**
     * Saves the booking to the database.
     * @return Booking ID or -1 on failure
     */
    public int saveToDB() throws SQLException {
        // checks if a database record already exists
        ResultSet data = DbConnection.executeQuery("SELECT id FROM bookings WHERE id = ?", this.id);
        if (data.next()) return -1;

        DbConnection.setAutoCommit(false);

        // lounge ids to db query format
        Array queryLoungeIdsData = DbConnection.stringListToVarcharArray(this.getLoungeIds());

        // saves booking to db
        boolean bookingsUpdatedSuccess = DbConnection.executeUpdate(
                "INSERT INTO bookings (beach_id, date, from_time, to_time, user_id, lounge_ids) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                this.beachID,
                this.date,
                this.fromTime,
                this.toTime,
                this.userID,
                queryLoungeIdsData
        ) == 1;

        DbConnection.commit();
        DbConnection.setAutoCommit(true);
        if (!bookingsUpdatedSuccess) return -1;

        // id of new booking
        ResultSet bookingData = DbConnection.executeQuery(
                "SELECT id FROM bookings ORDER BY id DESC"
        );
        if (bookingData == null || !bookingData.next()) return -1;
        return bookingData.getInt("id");
    }
}
