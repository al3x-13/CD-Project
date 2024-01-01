package cd.project.client.core;

import cd.project.backend.domain.Booking;
import cd.project.client.Main;
import cd.project.client.Router;
import cd.project.frontend.soap.entities.BookingSoap;
import jakarta.xml.ws.WebServiceException;
import org.apache.cxf.transport.http.HTTPException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SoapUtilities {
    public static void checkUnauthorizedStatus(Exception e) throws UnauthorizedException {
        if (e instanceof WebServiceException) {
            Throwable cause = e.getCause();
            if (cause instanceof HTTPException) {
                HTTPException httpException = (HTTPException) cause;
                if (httpException.getResponseCode() == 401) {
                    throw new UnauthorizedException("Unauthorized Request", cause);
                }
            }
        }
    }

    public static void handleExpiredSession() {
        UserSession.invalidateSession();
        Main.sessionExpiredNotification = true;
        Router.navigateToLogin();
    }

    public static Booking soapBookingToBooking(BookingSoap bookingSoap) {
        return new Booking(
                bookingSoap.getId(),
                bookingSoap.getBeachID(),
                LocalDate.parse(bookingSoap.getDate()),
                LocalTime.parse(bookingSoap.getFromTime()),
                LocalTime.parse(bookingSoap.getToTime()),
                LocalDateTime.parse(bookingSoap.getCreatedAt()),
                bookingSoap.getUserID(),
                bookingSoap.getLounges()
        );
    }
}
