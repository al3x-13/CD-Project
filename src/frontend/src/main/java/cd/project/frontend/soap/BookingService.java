package cd.project.frontend.soap;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.frontend.soap.entities.BookingSoap;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@WebService
public interface BookingService {
    @WebMethod
    ArrayList<Lounge> getAvailableLounges(char beachId, String date, String fromTime, String toTime);

    @WebMethod
    int createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals,
            int userId
    );

    @WebMethod
    boolean cancelBooking(int bookingId);

    @WebMethod
    ArrayList<BookingSoap> getUserBookings();

    @WebMethod
    String test();
}
