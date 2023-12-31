package cd.project.client.core;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.client.CommunicationProtocol;
import cd.project.client.Main;
import cd.project.frontend.soap.entities.BookingSoap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

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
            try {
                return BookingServiceRest.getAvailableLounges(beachId, date, fromTime, toTime);
            } catch (UnauthorizedException e) {
                RestUtilities.handleExpiredSession();
            }
        }
        return null;
    }

    public static ArrayList<Booking> getUserBookings() {
        if (proto == CommunicationProtocol.SOAP) {
            try {
                ArrayList<Booking> bookings = new ArrayList<>();

                ArrayList<BookingSoap> userBookings = BookingServiceSoap.getUserBookings();
                if (userBookings == null) return null;
                for (BookingSoap userBooking : userBookings) {
                    bookings.add(SoapUtilities.soapBookingToBooking(userBooking));
                }
                return bookings;
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }
        } else {
            try {
                return BookingServiceRest.getUserBookings();
            } catch (UnauthorizedException e) {
                RestUtilities.handleExpiredSession();
            }
        }
        return null;
    }

    public static ArrayList<Lounge> checkBookingAvailability(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals
    ) {
        if (proto == CommunicationProtocol.SOAP) {
            try {
                return BookingServiceSoap.checkBookingAvailability(beachId, date, fromTime, toTime, individuals);
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }
        } else {
            try {
                return BookingServiceRest.checkBookingAvailability(beachId, date, fromTime, toTime, individuals);
            } catch (UnauthorizedException e) {
                RestUtilities.handleExpiredSession();
            }
        }
        return null;
    }

    public static int createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals
    ) {
        if (proto == CommunicationProtocol.SOAP) {
            try {
                return BookingServiceSoap.createBooking(beachId, date, fromTime, toTime, individuals);
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }
        } else {
            try {
                return BookingServiceRest.createBooking(beachId, date, fromTime, toTime, individuals);
            } catch (UnauthorizedException e) {
                RestUtilities.handleExpiredSession();
            }
        }
        return -1;
    }

    public static boolean cancelBooking(int bookingId) {
        if (proto == CommunicationProtocol.SOAP) {
            try {
                return BookingServiceSoap.cancelBooking(bookingId);
            } catch (UnauthorizedException e) {
                SoapUtilities.handleExpiredSession();
            }
        } else {
            try {
                return BookingServiceRest.cancelBooking(bookingId);
            } catch (UnauthorizedException e) {
                RestUtilities.handleExpiredSession();
            }
        }
        return false;
    }
}
