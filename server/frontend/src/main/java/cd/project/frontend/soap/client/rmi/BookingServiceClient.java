package cd.project.frontend.soap.client.rmi;

import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.backend.services.BookingService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BookingServiceClient {
    public BookingServiceInterface BookingServiceClient() {
        BookingServiceInterface bookingService;
        try {
            bookingService = new BookingService();

            // create stub
            UnicastRemoteObject.unexportObject((BookingServiceInterface) bookingService, true);
            BookingServiceInterface bookingServiceStub = (BookingServiceInterface)
                    UnicastRemoteObject.exportObject((BookingServiceInterface) bookingService, 0);

            // registry setup (local)
            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(System.getenv("port")));
            registry.bind("BookingService", bookingServiceStub);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate booking service: " + e);
        }
        return bookingService;
    }
}
