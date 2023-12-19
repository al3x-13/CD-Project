package cd.project.frontend.soap;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.frontend.soap.client.rmi.BookingServiceClient;
import jakarta.jws.WebService;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@WebService(endpointInterface = "cd.frontend.soap.BookingService", name = "BookingService")
public class BookingServiceImpl implements BookingService {
    private final BookingServiceInterface bookingService = (BookingServiceInterface) new BookingServiceClient();

    @Override
    public ArrayList<Lounge> getAvailableLounges(char beachId, LocalDate date, LocalTime fromTime, LocalTime toTime) {
        try {
            return bookingService.listAvailableLounges(beachId, date, fromTime, toTime);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    public int createBooking(char beachId, LocalDate date, LocalTime fromTime, LocalTime toTime, int individuals, int userId) {
        try {
            return bookingService.createBooking(beachId, date, fromTime, toTime, individuals, userId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        try {
            return bookingService.cancelBooking(bookingId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    @Override
    public ArrayList<Booking> getUserBookings(int userId) {
        try {
            return bookingService.getUserBookings(userId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }
}
