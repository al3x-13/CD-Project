package cd.project.frontend.soap;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.frontend.auth.JwtHelper;
import cd.project.frontend.soap.client.rmi.BookingServiceClient;
import cd.project.frontend.soap.entities.BookingSoap;
import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@WebService(endpointInterface = "cd.project.frontend.soap.BookingService", serviceName = "BookingService")
public class BookingServiceImpl implements BookingService {
    private final BookingServiceInterface bookingService = new BookingServiceClient().getBookingService();
    @Resource
    private WebServiceContext webServiceContext;

    @Override
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
    public ArrayList<Lounge> checkBookingAvailability(
            char beachId,
            String date,
            String fromTime,
            String toTime,
            int individuals
    ) {
        try {
            return bookingService.checkBookingAvailability(
                    beachId,
                    LocalDate.parse(date),
                    LocalTime.parse(fromTime),
                    LocalTime.parse(toTime),
                    individuals
            );
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    public int createBooking(char beachId, String date, String fromTime, String toTime, int individuals) {
        int userId = this.getUserIdFromJWT();

        try {
            return bookingService.createBooking(
                    beachId,
                    LocalDate.parse(date),
                    LocalTime.parse(fromTime),
                    LocalTime.parse(toTime),
                    individuals,
                    userId
            );
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
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
    public ArrayList<BookingSoap> getUserBookings() {
        int userId = this.getUserIdFromJWT();

        try {
            ArrayList<Booking> bookings = bookingService.getUserBookings(userId);
            return parseBookingsToSoapBookings(bookings);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    private static ArrayList<BookingSoap> parseBookingsToSoapBookings(ArrayList<Booking> bookings) {
        ArrayList<BookingSoap> soapBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingSoap soapBooking = new BookingSoap(
                    booking.getId(),
                    booking.getBeachID(),
                    booking.getDate().toString(),
                    booking.getFromTime().toString(),
                    booking.getToTime().toString(),
                    booking.getCreatedAt().toString(),
                    booking.getUserID(),
                    booking.getLounges()
            );
            soapBookings.add(soapBooking);
        }
        return soapBookings;
    }

    public String test() {
        return "DEEZ NUTZ";
    }


    private int getUserIdFromJWT() {
        MessageContext mc = this.webServiceContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) mc.get("HTTP.REQUEST");
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.split(" ")[1];
        return JwtHelper.getUserId(token);
    }
}
