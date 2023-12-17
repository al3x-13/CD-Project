package cd.project.backend.services;

import cd.project.backend.database.DbConnection;
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
        ArrayList<Lounge> out = BookingServiceHelpers.getAvailableLounges(
                'A',
                LocalDate.of(2023, 11, 29),
                LocalTime.of(10, 00),
                LocalTime.of(11, 00)
        );
        return "TESTING: " + Arrays.toString(new ArrayList[]{out});
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
        return false;
    }
}
