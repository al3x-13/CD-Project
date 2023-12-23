package cd.project.frontend.soap;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TestClient {
    public static void main(String[] args) {
        // Auth service
        JaxWsProxyFactoryBean authFactory = new JaxWsProxyFactoryBean();
        authFactory.setAddress("http://localhost:8080/frontend/soap/auth");
        authFactory.setServiceClass(Authentication.class);
        Authentication authService = (Authentication) authFactory.create();
        Map<String, Object> authRequestCtx = ((BindingProvider) authService).getRequestContext();

        // Booking service
        JaxWsProxyFactoryBean bookingFactory = new JaxWsProxyFactoryBean();
        bookingFactory.setAddress("http://localhost:8080/frontend/soap/booking");
        bookingFactory.setServiceClass(BookingService.class);
        BookingService bookingService = (BookingService) bookingFactory.create();
        Map<String, Object> bookingRequestCtx = ((BindingProvider) bookingService).getRequestContext();

        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("client# ");
                String input = scanner.nextLine();

                if (Objects.equals(input, "exit") || Objects.equals(input, "quit")) {
                    return;
                }

                System.out.println(execute(scanner, authService, bookingService, authRequestCtx, bookingRequestCtx, input));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private static String execute(
            Scanner scanner,
            Authentication authService,
            BookingService bookingService,
            Map<String, Object> requestCtxAuth,
            Map<String, Object> requestCtxBook,
            String command
    ) {
        String username, password;

        switch (command) {
            case "login":
                System.out.println("--- User Authentication ---");
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                System.out.print("Enter password: ");
                password = scanner.nextLine();
                String sessionToken = authService.authenticate(username, password);
                if (sessionToken == null) return "FAILURE";
                setAuthHeader(requestCtxAuth, requestCtxBook, sessionToken);
                return "SUCCESS";
            case "register":
                System.out.println("--- User Registration ---");
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                System.out.print("Enter password: ");
                password = scanner.nextLine();
                boolean success = authService.register(username, password);
                return success ? "SUCCESS" : "FAILURE";
            case "test":
                return bookingService.getAvailableLounges(
                        'A',
                        LocalDate.of(2024, 6, 12).toString(),
                        LocalTime.of(10, 0).toString(),
                        LocalTime.of(11, 0).toString()
                ).get(0).toString();
            default:
                return "U dumb?!";
        }
    }

    private static void setAuthHeader(Map<String, Object> requestCtxAuth, Map<String, Object> requestCtxBook, String sessionToken) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Collections.singletonList("Bearer " + sessionToken));
        requestCtxAuth.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        requestCtxBook.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }
}
