package cd.project.backend.services;

import cd.project.backend.database.DbConnection;
import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceHelpers {
    /**
     * Gets Ids from Bookings for the specified date and in the specified time interval.
     * @param date date input
     * @param fromTime time interval start
     * @param toTime time interval end
     * @return Booking IDs or null if none
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
     * @return Available Lounges or null if none
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

    /**
     * Gets total number of lounge seats from lounge list.
     * @param lounges lounges list
     * @return Total lounge seats
     */
    public static int getTotalLoungeSeats(ArrayList<Lounge> lounges) {
        int totalSeats = 0;
        for (Lounge lounge : lounges) {
            totalSeats += lounge.getMaxCapacity();
        }
        return totalSeats;
    }

    /**
     * Checks for booking availability for with the provided details.
     * @param beachId beach id
     * @param date date
     * @param fromTime from time
     * @param toTime to time
     * @param individuals amount of people
     * @return Available lounges or null if no availability
     */
    public static ArrayList<Lounge> checkBookingAvailability(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals
    ) {
        ArrayList<Lounge> availableLounges = getAvailableLounges(beachId, date, fromTime, toTime);
        ArrayList<Lounge> bookingLounges = new ArrayList<>();
        if (availableLounges == null) return null;
        int totalSeats = getTotalLoungeSeats(availableLounges);
        int remainingSeats = individuals;

        if (totalSeats < individuals) {
            return null;
        }

        while (remainingSeats > 0) {
            if (remainingSeats >= 4 && verifySeatAvailability(availableLounges, 4)) {
                remainingSeats -= 4;
                moveLoungeFromAvailableToBooked(availableLounges, bookingLounges, 4);
            } else if (remainingSeats >= 3 && verifySeatAvailability(availableLounges, 3)) {
                remainingSeats -= 3;
                moveLoungeFromAvailableToBooked(availableLounges, bookingLounges, 3);
            } else {
                remainingSeats -= 2;
                moveLoungeFromAvailableToBooked(availableLounges, bookingLounges, 2);;
            }
        }
        return bookingLounges;
    }

    /**
     * Creates a new booking from the provided details.
     * @param beachId beach id
     * @param date date
     * @param fromTime from time
     * @param toTime to time
     * @param individuals amount of people
     * @return Whether booking was created successfully
     */
    public static int createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals,
            int userId
    ) {
        ArrayList<Lounge> availableLounges = getAvailableLounges(beachId, date, fromTime, toTime);
        ArrayList<Lounge> bookingLounges = new ArrayList<>();
        if (availableLounges == null) return -1;
        int totalSeats = getTotalLoungeSeats(availableLounges);
        int remainingSeats = individuals;

        if (totalSeats < individuals) {
            return -1;
        }

        while (remainingSeats > 0) {
            if (remainingSeats >= 4 && verifySeatAvailability(availableLounges, 4)) {
                remainingSeats -= 4;
                moveLoungeFromAvailableToBooked(availableLounges, bookingLounges, 4);
            } else if (remainingSeats >= 3 && verifySeatAvailability(availableLounges, 3)) {
                remainingSeats -= 3;
                moveLoungeFromAvailableToBooked(availableLounges, bookingLounges, 3);
            } else {
                remainingSeats -= 2;
                moveLoungeFromAvailableToBooked(availableLounges, bookingLounges, 2);;
            }
        }

        Booking newBooking = new Booking(
                beachId,
                date,
                fromTime,
                toTime,
                userId,
                bookingLounges
        );
        try {
            return newBooking.saveToDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes a single lounge (for the specified capacity) from a lounges list.
     * @param availableLounges lounges list
     * @param loungeCapacity type of lounge to be removed
     */
    private static boolean moveLoungeFromAvailableToBooked(ArrayList<Lounge> availableLounges, ArrayList<Lounge> bookingLounges, int loungeCapacity) {
        if (loungeCapacity != 2 && loungeCapacity != 3 && loungeCapacity != 4) return false;

        for (Lounge lounge : availableLounges) {
            if (lounge.getMaxCapacity() == loungeCapacity) {
                bookingLounges.add(lounge);
                availableLounges.remove(lounge);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks lounge type availability based on provided lounge capacity in a lounges list.
     * @param lounges lounges list
     * @return Whether lounge for the specified capacity is available
     */
    private static boolean verifySeatAvailability(ArrayList<Lounge> lounges, int loungeCapacity) {
        if (loungeCapacity != 2 && loungeCapacity != 3 && loungeCapacity != 4) return false;

        for (Lounge lounge : lounges) {
            if (lounge.getMaxCapacity() == loungeCapacity) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cancels a booking by id.
     * @param bookingId booking id
     * @return SUCCESS
     */
    public static boolean cancelBooking(int bookingId) {
        return DbConnection.executeUpdate(
                "DELETE FROM bookings WHERE id = ?",
                bookingId
        ) == 1;
    }

    /**
     * Gets all the bookings for the specified user id.
     * @param userId user id
     * @return Bookings List
     */
    public static ArrayList<Booking> getUserBookings(int userId) {
        ArrayList<Booking> userBookings = new ArrayList<>();

        ResultSet bookingsData = DbConnection.executeQuery(
                "SELECT id, beach_id, date, from_time, to_time, created_at, lounge_ids " +
                        "FROM bookings WHERE user_id = ?",
                userId
        );

        while (true) {
            try {
                if (!bookingsData.next()) break;
                int id = bookingsData.getInt("id");
                char beachId = bookingsData.getString("beach_id").charAt(0);
                LocalDate date = bookingsData.getDate("date").toLocalDate();
                LocalTime fromTime = bookingsData.getTime("from_time").toLocalTime();
                LocalTime toTime = bookingsData.getTime("to_time").toLocalTime();
                LocalDateTime createdAt = bookingsData.getObject("created_at", LocalDateTime.class);
                ArrayList<String> loungeIds = new ArrayList<>(
                        List.of((String[]) bookingsData
                        .getArray("lounge_ids")
                        .getArray())
                );
                userBookings.add(
                        new Booking(
                                id,
                                beachId,
                                date,
                                fromTime,
                                toTime,
                                createdAt,
                                userId,
                                Lounge.getLoungesByID(loungeIds)
                        )
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return userBookings;
    }
}
