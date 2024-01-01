package cd.project.frontend.rest;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.frontend.rest.entities.AvailableLoungesInput;
import cd.project.frontend.rest.entities.BookingAvailabilityInput;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface BookingService {
    ArrayList<Lounge> getAvailableLounges(AvailableLoungesInput data);

    ArrayList<Lounge> checkBookingAvailability(BookingAvailabilityInput data);

    int createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals
    );

    boolean cancelBooking(int bookingId);

    ArrayList<Booking> getUserBookings();
}
