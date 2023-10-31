package cd.project.backend;

import cd.project.backend.services.ReservationServiceInterface;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestClient {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.err.println("Usage: java TestClient port data...");
                System.exit(1);
            }

            int port = Integer.parseInt(args[0]);
            /*String url = "//localhost:" + port + "/ReservationService";
            System.out.println("Looking up " + url);
            ReservationServiceInterface server = (ReservationServiceInterface) Naming.lookup(url);*/

            Registry registry = LocateRegistry.getRegistry(port);
            ReservationServiceInterface server = (ReservationServiceInterface) registry.lookup("ReservationService");

            for (int i = 1; i < args.length; i++) {
                System.out.println(server.test(args[i]));
            }
        } catch(Exception e) {
            System.err.println("Client failed: " + e.getMessage());
        }
    }
}
