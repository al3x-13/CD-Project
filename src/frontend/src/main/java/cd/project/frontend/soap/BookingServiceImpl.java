package cd.project.frontend.soap;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.frontend.auth.JwtHelper;
import cd.project.frontend.soap.client.rmi.BookingServiceClient;
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
    public ArrayList<Booking> getUserBookings() {
        MessageContext mc = webServiceContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) mc.get("HTTP.REQUEST");
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.split(" ")[1];
        int userId = JwtHelper.getUserId(token);

        try {
            return bookingService.getUserBookings(userId);
        } catch (RemoteException e) {
            throw new RuntimeException("Failed to execute remote method: " + e);
        }
    }

    public String test() {
        return "DEEZ NUTZ";
    }
}
