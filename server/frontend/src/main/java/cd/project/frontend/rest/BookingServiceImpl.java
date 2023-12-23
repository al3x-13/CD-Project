package cd.project.frontend.rest;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.frontend.soap.client.rmi.BookingServiceClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/booking")
public class BookingServiceImpl implements BookingService {
    private final BookingServiceInterface bookingService = new BookingServiceClient().getBookingService();

    @Override
    @GET
    @Path("/getAvailableLouges")
    public ArrayList<Lounge> getAvailableLounges(char beachId, String date, String fromTime, String toTime) {
        try {
            return bookingService.listAvailableLounges(
                    beachId,
                    LocalDate.parse(date),
                    LocalTime.parse(fromTime),
                    LocalTime.parse(toTime)
            );
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    @POST
    @Path("/createBooking")
    public int createBooking(char beachId, LocalDate date, LocalTime fromTime, LocalTime toTime, int individuals, int userId) {
        try {
            return bookingService.createBooking(beachId, date, fromTime, toTime, individuals, userId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    @POST
    @Path("/cancelBooking")
    public boolean cancelBooking(int bookingId) {
        try {
            return bookingService.cancelBooking(bookingId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    @GET
    @Path("/getUserBookings")
    public ArrayList<Booking> getUserBookings(int userId) {
        try {
            return bookingService.getUserBookings(userId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @GET
    @Path("/test")
    public String test() {
        return "DEEZ NUTZ";
    }
}
