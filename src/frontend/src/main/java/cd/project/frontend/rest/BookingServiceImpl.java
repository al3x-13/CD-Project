package cd.project.frontend.rest;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.frontend.auth.JwtHelper;
import cd.project.frontend.rest.entities.AvailableLoungesInput;
import cd.project.frontend.rest.entities.BookingAvailabilityInput;
import cd.project.frontend.rest.entities.CreateBookingInput;
import cd.project.frontend.soap.client.rmi.BookingServiceClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/booking")
public class BookingServiceImpl implements BookingService {
    private final BookingServiceInterface bookingService = new BookingServiceClient().getBookingService();
    @Context
    private HttpHeaders headers;

    @Override
    @POST
    @Path("/getAvailableLounges")
    public ArrayList<Lounge> getAvailableLounges(AvailableLoungesInput data) {
        try {
            return bookingService.listAvailableLounges(
                    data.getBeachId(),
                    LocalDate.parse(data.getDate()),
                    LocalTime.parse(data.getFromTime()),
                    LocalTime.parse(data.getToTime())
            );
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    @POST
    @Path("/checkBookingAvailability")
    public ArrayList<Lounge> checkBookingAvailability(BookingAvailabilityInput data) {
        try {
            return bookingService.checkBookingAvailability(
                    data.getBeachId(),
                    LocalDate.parse(data.getDate()),
                    LocalTime.parse(data.getFromTime()),
                    LocalTime.parse(data.getToTime()),
                    data.getIndividuals()
            );
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    @POST
    @Path("/createBooking")
    public int createBooking(CreateBookingInput data) {
        int userId = this.getUserIdFromJWT();
        try {
            return bookingService.createBooking(
                    data.getBeachId(),
                    LocalDate.parse(data.getDate()),
                    LocalTime.parse(data.getFromTime()),
                    LocalTime.parse(data.getToTime()),
                    data.getIndividuals(),
                    userId
            );
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    @POST
    @Path("/cancelBooking")
    public boolean cancelBooking(int bookingId) {
        int userId = this.getUserIdFromJWT();
        try {
            if (!bookingService.userOwnsBooking(userId, bookingId)) {
                return false;
            }
            return bookingService.cancelBooking(bookingId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    @GET
    @Path("/getUserBookings")
    public ArrayList<Booking> getUserBookings() {
        int userId = this.getUserIdFromJWT();

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

    private int getUserIdFromJWT() {
        String authHeader = headers.getRequestHeader("Authorization").getFirst();
        String token = authHeader.split(" ")[1];
        return JwtHelper.getUserId(token);
    }
}
