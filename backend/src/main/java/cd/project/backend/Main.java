package cd.project.backend;

import cd.project.backend.database.DbConnection;
import cd.project.backend.services.BookingService;
import cd.project.backend.services.BookingServiceInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Main rmi_port db_path");
            System.exit(1);
        }

        try {
            int port = Integer.parseInt(args[0]);

            // services
            BookingServiceInterface reservationService = new BookingService();

            // stubs
            UnicastRemoteObject.unexportObject((BookingServiceInterface) reservationService, true);
            BookingServiceInterface reservationServiceStub = (BookingServiceInterface)
                    UnicastRemoteObject.exportObject((BookingServiceInterface) reservationService, 0);

            // local registry
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("ReservationService", reservationServiceStub);

            // Initializes DB connection
            DbConnection db = new DbConnection(args[1]);

            System.out.println("RMI server is running");
        } catch (Exception e) {
            System.err.println("RMI Server failed: " + e.getMessage());
            System.exit(1);
        }
    }
}