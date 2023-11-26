package cd.project.backend.services;

import cd.project.rmi.interfaces.BookingServiceInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BookingService extends UnicastRemoteObject implements BookingServiceInterface {
    public BookingService() throws RemoteException {}

    @Override
    public String test(String data) throws RemoteException {
        return "test: " + data;
    }
}
