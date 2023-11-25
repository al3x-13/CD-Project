package cd.project.backend.domain;

import cd.project.backend.database.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Booking {
    private int id;
    private char beachID;
    private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private LocalDateTime createdAt;
    private int userID;

    // TODO: add lounges data

    /**
     * Creates a new lounge booking and saves it to the database.
     * @param beachID beach identifier
     * @param date booking date
     * @param fromTime start time
     * @param toTime end time
     * @param userID user identifier
     */
    public Booking(DbConnection db, char beachID, LocalDate date, LocalTime fromTime, LocalTime toTime, int userID) throws SQLException {
        this.beachID = beachID;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.userID = userID;
        this.saveToDB(db);
    }

    /**
     * Creates a new lounge booking object from an existing database booking.
     * @param db database connection
     * @param bookingID booking identifier
     */
    public Booking(DbConnection db, int bookingID) throws Exception {
        this.id = bookingID;

        ResultSet data = db.executeQuery("SELECT * FROM bookings WHERE id = ?", bookingID);
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

    private boolean saveToDB(DbConnection db) throws SQLException {
        // checks if a database record already exists
        ResultSet data = db.executeQuery("SELECT id FROM bookings WHERE id = ?", this.id);
        if (!data.next()) return false;

        db.setAutoCommit(false);

        // saves booking to db
        boolean bookingsUpdated = db.executeUpdate(
                "UPDATE bookings SET beach_id = ?, date = ?, from_time = ?, to_time = ?",
                this.beachID,
                this.date,
                this.fromTime,
                this.toTime
        ) == 1;

        // TODO: finish this when data about lounges has been added
        boolean bookingLoungesUpdated = db.executeUpdate(
                ""
        ) == 1;

        return bookingsUpdated && bookingLoungesUpdated;
    }
}
