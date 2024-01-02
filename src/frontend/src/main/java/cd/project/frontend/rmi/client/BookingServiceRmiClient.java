package cd.project.frontend.rmi.client;

import cd.project.backend.interfaces.BookingServiceInterface;
import cd.project.backend.services.BookingService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingServiceRmiClient {
    private static final ArrayList<BookingServiceInterface> clients = discoverRmiRegistries();
    private static final AtomicInteger currentClientIndex = new AtomicInteger(0);

    // Returns rmi registry using 'Round-Robin' style algorithm
    public static BookingServiceInterface getClient() {
        currentClientIndex.getAndUpdate(index -> (index + 1) % clients.size());
        return clients.get(currentClientIndex.get());
    }

    public static int getIndex(BookingServiceInterface client) {
        return clients.indexOf(client);
    }

    public static int getPort(BookingServiceInterface client) {
        try {
            return clients.get(clients.indexOf(client)).getPort();
        } catch (RemoteException e) {
            return -1;
        }
    }

    // Discovers RMI registries on localhost in port 1099 or in port range 1300-1399
    // Returns the registry clients on those ports
    private static ArrayList<BookingServiceInterface> discoverRmiRegistries() {
        ArrayList<BookingServiceInterface> clients = new ArrayList<>();

        BookingServiceInterface client1099 = createClient(1099);
        if (client1099 != null) {
            clients.add(client1099);
        }

        for (int port = 1300; port <= 1399; port++){
            BookingServiceInterface client = createClient(port);
            if (client != null) {
                clients.add(client);
                System.out.println("[REGISTRY DISCOVERY] Discovered registry at port '" + port + "'");
            }
        }
        return clients;
    }

    // Creates a client for the registry running on the provided port
    // If one exists, otherwise returns null
    private static BookingServiceInterface createClient(int port) {
        BookingServiceInterface client;
        try {
            client = new BookingService();

            UnicastRemoteObject.unexportObject((BookingServiceInterface) client, true);
            BookingServiceInterface bookingServiceStub = (BookingServiceInterface)
                    UnicastRemoteObject.exportObject((BookingServiceInterface) client, 0);

            Registry registry = null;
                registry = LocateRegistry.getRegistry("localhost", port);
            try {
                registry.bind("BookingService", bookingServiceStub);
            } catch (AlreadyBoundException e) {
                registry.rebind("BookingService", bookingServiceStub);
            }
        } catch (RemoteException e) {
            return null;
        }
        return client;
    }
}
