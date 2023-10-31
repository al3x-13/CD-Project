package cd.project.backend.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReservationService extends UnicastRemoteObject implements ReservationServiceInterface {
    public ReservationService() throws RemoteException {}

    @Override
    public String test(String data) throws RemoteException {
        return "test: " + data;
    }
}
