package cd.project.backend.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReservationServiceInterface extends Remote {
    public String test(String data) throws RemoteException;
}
