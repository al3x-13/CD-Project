package cd.project.frontend.rest;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface BookingService {
    ArrayList<Lounge> getAvailableLounges(char beachId, String date, String fromTime, String toTime);

    int createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals,
            int userId
    );

    boolean cancelBooking(int bookingId);

    ArrayList<Booking> getUserBookings(int userId);
}