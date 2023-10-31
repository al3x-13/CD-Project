package cd.project.backend;

import cd.project.backend.database.DbConnection;
import cd.project.backend.services.ReservationService;
import cd.project.backend.services.ReservationServiceInterface;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Main rmi_port db_path");
            System.exit(1);
        }

        try {
            int port = Integer.parseInt(args[0]);
            /*String url = "//localhost:" + port + "/ReservationService";
            Naming.rebind(url, new ReservationService());*/

            ReservationServiceInterface server = new ReservationService();
            UnicastRemoteObject.unexportObject((ReservationServiceInterface) server, true);
            ReservationServiceInterface stub = (ReservationServiceInterface) UnicastRemoteObject.exportObject((ReservationServiceInterface) server, 0);

            // local registry
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("ReservationService", stub);

            // Initializes DB connection
            DbConnection.initialize(args[1]);

            System.out.println("RMI server is running");
        } catch (Exception e) {
            System.err.println("RMI Server failed: " + e.getMessage());
            System.exit(1);
        }
    }
}