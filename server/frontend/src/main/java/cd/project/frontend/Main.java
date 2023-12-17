package cd.project.frontend;

import cd.project.backend.interfaces.BookingServiceInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                System.err.println("Usage: java Main");
                System.exit(1);
            }

            // rmi setup
            Registry registry = LocateRegistry.getRegistry();
            BookingServiceInterface server = (BookingServiceInterface) registry.lookup("ReservationService");

            System.out.println("Frontend server running...");

            // TODO
            String output = server.test("yes daddy");
            System.out.println(output);
            System.out.println("Success: " + server.createBooking(
                    'C',
                    LocalDate.of(2024, 6, 14),
                    LocalTime.of(11, 0),
                    LocalTime.of(14, 0),
                    6,
                    1
            ));
        } catch(Exception e) {
            System.err.println("Client failed: " + e.getMessage());
        }
    }
}