package cd.project.backend.services;

import cd.project.backend.domain.Booking;
import cd.project.backend.domain.Lounge;
import cd.project.backend.interfaces.BookingServiceInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class BookingService extends UnicastRemoteObject implements BookingServiceInterface {
    public BookingService() throws RemoteException {}

    @Override
    public String test(String data) throws RemoteException {
        return "TESTING: " + data;
    }

    @Override
    public ArrayList<Lounge> listAvailableLounges(char beachId, LocalDate date, LocalTime fromTime, LocalTime toTime)
            throws RemoteException {
        return BookingServiceHelpers.getAvailableLounges(
                beachId,
                date,
                fromTime,
                toTime
        );
    }

    @Override
    public ArrayList<Lounge> checkBookingAvailability(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals,
            int userId
    ) throws RemoteException {
        return BookingServiceHelpers.checkBookingAvailability(beachId, date, fromTime, toTime, individuals);
    }

    @Override
    public int createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals,
            int userId
    )
            throws RemoteException {
        return BookingServiceHelpers.createBooking(beachId, date, fromTime, toTime, individuals, userId);
    }

    @Override
    public boolean cancelBooking(int bookingId) throws RemoteException {
        return BookingServiceHelpers.cancelBooking(bookingId);
    }

    @Override
    public ArrayList<Booking> getUserBookings(int userId) throws RemoteException {
       return BookingServiceHelpers.getUserBookings(userId);
    }
}
