package cd.project.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BookingServiceInterface extends Remote {
    public String test(String data) throws RemoteException;
}
