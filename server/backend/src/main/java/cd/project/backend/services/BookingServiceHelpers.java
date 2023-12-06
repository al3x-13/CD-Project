package cd.project.backend.services;

import cd.project.backend.database.DbConnection;
import cd.project.backend.domain.Lounge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookingServiceHelpers {
    /**
     * Gets Ids from Bookings for the specified date.
     * @param date date input
     * @return Booking IDs
     */
    public static ArrayList<Integer> getBookingIdsForDate(LocalDate date) {
        ArrayList<Integer> ids = new ArrayList<>();

        ResultSet data = DbConnection.executeQuery("SELECT id FROM bookings WHERE date = ?", date);
        while (true) {
            try {
                if (!data.next()) break;
                ids.add(data.getInt("id"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ids.isEmpty() ? null : ids;
    }

    /**
     * Gets Ids from Bookings for the specified date and time interval.
     * @param date date input
     * @param fromTime time interval start
     * @param toTime time interval end
     * @return Booking IDs
     */
    public static ArrayList<Integer> getBookingIdsForDateAndTime(
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime
    ) {
        ArrayList<Integer> ids = new ArrayList<>();

        ResultSet data = DbConnection.executeQuery(
                "SELECT id FROM bookings WHERE date = ? AND from_time = ? AND to_time = ?",
                date,
                fromTime,
                toTime
        );
        while (true) {
            try {
                if (!data.next()) break;
                ids.add(data.getInt("id"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ids.isEmpty() ? null : ids;
    }

    /**
     * Gets all available lounges from the selected beach for the specified date and time.
     * @param beachId beach id
     * @param date date
     * @param fromTime start time
     * @param toTime end time
     * @return Available Lounges
     */
    public static ArrayList<Lounge> getAvailableLounges(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime
    ) {
        ArrayList<Lounge> lounges = new ArrayList<>();
        ResultSet data = DbConnection.executeQuery(
                "SELECT * FROM lounges WHERE beach_id = ? AND id NOT IN (SELECT UNNEST(lounge_ids) FROM bookings WHERE date = ? AND from_time < ? AND to_time > ?)",
                beachId,
                date,
                toTime,
                fromTime
        );
        while (true) {
            try {
                if (!data.next()) break;
                String id = data.getString("id");
                char beach = data.getString("beach_id").charAt(0);
                int maxCapacity = data.getInt("max_capacity");
                lounges.add(new Lounge(id, beach, maxCapacity));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return lounges.isEmpty() ? null : lounges;
    }
}
