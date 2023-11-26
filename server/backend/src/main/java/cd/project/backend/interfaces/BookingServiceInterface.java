package cd.project.backend.interfaces;

import cd.project.backend.domain.Lounge;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface BookingServiceInterface extends Remote {
    String test(String data) throws RemoteException;

   ArrayList<Lounge> listAvailableLounges(
           char beachId, LocalDate date, LocalTime fromTime, LocalTime toTime
   ) throws RemoteException;
}
