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
    private final DbConnection db;

    public BookingService(DbConnection db) throws RemoteException {
        this.db = db;
    }

    @Override
    public String test(String data) throws RemoteException {
        ArrayList<Lounge> out = BookingServiceHelpers.getAvailableLounges(
                this.db,
                'A',
                LocalDate.of(2023, 11, 29),
                LocalTime.of(10, 00),
                LocalTime.of(11, 00)
        );
        return "TESTING: " + Arrays.toString(new ArrayList[]{out});
    }

    @Override
    public ArrayList<Lounge> listAvailableLounges(char beachId, LocalDate date, LocalTime fromTime, LocalTime toTime) throws RemoteException {
        return null;
    }
}
