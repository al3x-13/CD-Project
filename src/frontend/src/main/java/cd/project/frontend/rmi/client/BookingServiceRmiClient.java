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

class RmiClient {
    private BookingServiceInterface client;
    private int port;

    public RmiClient(BookingServiceInterface client, int port) {
        this.client = client;
        this.port = port;
    }

    public BookingServiceInterface getClient() {
        return client;
    }

    public int getPort() {
        return port;
    }
}

public class BookingServiceRmiClient {
    private static final ArrayList<RmiClient> clients = discoverRmiRegistries();
    private static final AtomicInteger currentClientIndex = new AtomicInteger(0);

    // Returns rmi registry using 'Round-Robin' style algorithm
    public static BookingServiceInterface getClient() {
        RmiClient rmiClient = clients.get(currentClientIndex.get());
        // TODO: logging
        System.out.println("[RmiClient:" + rmiClient.getPort() + "] -> Client selected");
        currentClientIndex.getAndUpdate(index -> (index + 1) % clients.size());
        return rmiClient.getClient();
    }

    // Discovers RMI registries on localhost in port 1099 or in port range 1300-1399
    // Returns the registry clients on those ports
    private static ArrayList<RmiClient> discoverRmiRegistries() {
        ArrayList<RmiClient> clients = new ArrayList<>();

        BookingServiceInterface client1099 = createClient(1099);
        if (client1099 != null) {
            RmiClient rmiClient = new RmiClient(client1099, 1099);
            clients.add(rmiClient);
        }

        for (int port = 1300; port <= 1399; port++){
            BookingServiceInterface client = createClient(port);
            if (client != null) {
                RmiClient rmiClient = new RmiClient(client, port);
                clients.add(rmiClient);
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
