package cd.project.client.core;

import cd.project.backend.domain.Lounge;
import cd.project.client.CommunicationProtocol;
import cd.project.client.Main;
import cd.project.frontend.soap.entities.BookingSoap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookingService {
    private static final CommunicationProtocol proto = Main.clientProtocol;

    public static ArrayList<Lounge> getAvailableLounges(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime
    ) {
        if (proto == CommunicationProtocol.SOAP) {
            try {
                return BookingServiceSoap.getAvailableLounges(beachId, date, fromTime, toTime);
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }
        } else {
            // TODO
        }
        return null;
    }

    public static ArrayList<BookingSoap> getUserBookings() {
        if (proto == CommunicationProtocol.SOAP) {
            try {
                return BookingServiceSoap.getUserBookings();
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }
        } else {
            // TODO
        }
        return null;
    }
}
