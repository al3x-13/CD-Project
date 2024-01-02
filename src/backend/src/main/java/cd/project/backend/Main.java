package cd.project.backend;

import cd.project.backend.services.BookingService;
import cd.project.backend.interfaces.BookingServiceInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    // --- KNOWN PROBLEM (low severity) ---
    // The server shuts down after some time. No exceptions are thrown nor logs.
    // It can be caused by GC due to objects not being strongly referenced in the code.
    // A possible solution to this would be to store the objects in a list or
    // call some objects periodically to make sure they don't get GCed.
    private static int port;

    public static void main(String[] args) {
        checkEnvironmentVariables();

        try {
            // services
            BookingServiceInterface reservationService = new BookingService();

            // stubs
            UnicastRemoteObject.unexportObject((BookingServiceInterface) reservationService, true);
            BookingServiceInterface reservationServiceStub = (BookingServiceInterface)
                    UnicastRemoteObject.exportObject((BookingServiceInterface) reservationService, 0);

            // local registry
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("ReservationService", reservationServiceStub);

            System.out.println("RMI server is running on port " + port);
        } catch (Exception e) {
            System.err.println("RMI Server failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void checkEnvironmentVariables() {
        if (System.getenv("SERVER_PORT") == null) {
            throw new RuntimeException("'SERVER_PORT' env variable does not exist");
        }
        port = Integer.parseInt(System.getenv("SERVER_PORT"));
        try {
            Integer.parseInt(System.getenv("SERVER_PORT"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("'SERVER_PORT' env variable must be a number");
        }
        if (System.getenv("DB_URL") == null) {
            throw new RuntimeException("'DB_URL' env variable does not exist");
        }
        if (System.getenv("DB_USERNAME") == null) {
            throw new RuntimeException("'DB_USERNAME' env variable does not exist");
        }
        if (System.getenv("DB_PASSWORD") == null) {
            throw new RuntimeException("'DB_PASSWORD' env variable does not exist");
        }
    }

    public static int getPort() {
        return port;
    }
}