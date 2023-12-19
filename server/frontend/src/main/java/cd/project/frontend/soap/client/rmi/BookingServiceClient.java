package cd.project.frontend.soap.client.rmi;

import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.backend.services.BookingService;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BookingServiceClient {
    private BookingServiceInterface bookingService;

    public BookingServiceClient() {
        try {
            this.bookingService = new BookingService();

            // create stub
            UnicastRemoteObject.unexportObject((BookingServiceInterface) this.bookingService, true);
            BookingServiceInterface bookingServiceStub = (BookingServiceInterface)
                    UnicastRemoteObject.exportObject((BookingServiceInterface) this.bookingService, 0);

            // registry setup (local)
            Registry registry = LocateRegistry.getRegistry();
            try {
                registry.bind("BookingService", bookingServiceStub);
            } catch (AlreadyBoundException e) {
                registry.rebind("BookingService", bookingServiceStub);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate booking service: " + e);
        }
    }

    public BookingServiceInterface getBookingService() {
        return this.bookingService;
    }
}
