package cd.project.client.core;

import cd.project.backend.domain.Lounge;
import cd.project.client.Main;
import cd.project.frontend.soap.BookingService;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BookingServiceSoap {
    private static final BookingService bookingService = instantiateBookingService();
    private static Map<String, Object> bookingServiceCtx;

    private static BookingService instantiateBookingService() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/soap/booking");
        factory.setServiceClass(BookingService.class);
        BookingService service = (BookingService) factory.create();
        bookingServiceCtx = ((BindingProvider) service).getRequestContext();
        return service;
    }

    public static String test() throws UnauthorizedException {
        String output = null;
        try {
            output = bookingService.test();
        } catch (Exception e) {
            SoapUtilities.checkUnauthorizedStatus(e);
        }
        System.out.println("TESTING: " + output);
        return output;
    }

    public static ArrayList<Lounge> getAvailableLounges(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime
    ) throws UnauthorizedException {
        String parsedDate = date.toString();
        String parsedFromTime = fromTime.toString();
        String parsedToTime = toTime.toString();

        try {
            return bookingService.getAvailableLounges(beachId, parsedDate, parsedFromTime, parsedToTime);
        } catch (Exception e) {
            SoapUtilities.checkUnauthorizedStatus(e);
            return null;
        }
    }

    public static void setAuthHeader(String token) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Collections.singletonList("Bearer " + token));
        bookingServiceCtx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }

    public static void resetAuthHeader() {
        bookingServiceCtx.clear();
    }
}
