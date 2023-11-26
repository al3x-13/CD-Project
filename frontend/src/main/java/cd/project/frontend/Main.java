package cd.project.frontend;

import cd.project.frontend.auth.AuthenticationService;
import cd.project.frontend.database.DbConnection;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                System.err.println("Usage: java Main rmi_server_port db_path...");
                System.exit(1);
            }
            int port = Integer.parseInt(args[0]);
            String dbPath = args[1];

            // initializing db connection and authentication
            DbConnection db = new DbConnection(dbPath);
            AuthenticationService auth = new AuthenticationService(db);

            // rmi setup
            Registry registry = LocateRegistry.getRegistry();

            System.out.println("Frontend server running...");

            // TODO
        } catch(Exception e) {
            System.err.println("Client failed: " + e.getMessage());
        }
    }
}