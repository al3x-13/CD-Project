package cd.project.frontend;

import cd.project.backend.interfaces.BookingServiceInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                System.err.println("Usage: java Main rmi_server_port db_url db_user db_password...");
                System.err.println("Usage example: java Main 1099 localhost:5432/db username password");
                System.exit(1);
            }
            int port = Integer.parseInt(args[0]);
            String dbPath = args[1];

            // initializing db connection and authentication
            // DbConnection db = new DbConnection(dbPath);
            // AuthenticationService auth = new AuthenticationService(db);


            // rmi setup
            Registry registry = LocateRegistry.getRegistry();
            BookingServiceInterface server = (BookingServiceInterface) registry.lookup("ReservationService");

            System.out.println("Frontend server running...");

            // TODO
            String output = server.test("yes daddy");
            System.out.println(output);
        } catch(Exception e) {
            System.err.println("Client failed: " + e.getMessage());
        }
    }
}