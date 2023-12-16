package cd.project.backend.services;

import cd.project.backend.database.DbConnection;
import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookingServiceHelpers {
    /**
     * Gets Ids from Bookings for the specified date and in the specified time interval.
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

    /**
     * Gets remaining lounge seats based on the provided input.
     * @param beachId beach id
     * @param date date
     * @param fromTime from time
     * @param toTime to time
     * @return Remaining lounge seats
     */
    public static int getRemainingLoungeSeats(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime
    ) {
        ArrayList<Lounge> remainingLounges = getAvailableLounges(
                beachId,
                date,
                fromTime,
                toTime
        );
        if (remainingLounges == null) return 0;

        int totalRemainingSeats = 0;
        for (Lounge lounge : remainingLounges) {
            totalRemainingSeats += lounge.getMaxCapacity();
        }
        return totalRemainingSeats;
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
     * Creates a new booking from the provided details.
     * @param beachId beach id
     * @param date date
     * @param fromTime from time
     * @param toTime to time
     * @param individuals amount of people
     * @return Whether booking was created successfully
     */
    public static boolean createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals,
            int userId
    ) {
        ArrayList<Lounge> availableLounges = getAvailableLounges(beachId, date, fromTime, toTime);
        ArrayList<Lounge> bookingLounges = new ArrayList<>();
        if (availableLounges == null) return false;
        int totalSeats = getTotalLoungeSeats(availableLounges);
        int remainingSeats = individuals;

        if (totalSeats < individuals) {
            return false;
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
}
