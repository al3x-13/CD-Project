package cd.project.client.core;

import cd.project.client.Main;
import cd.project.frontend.soap.BookingService;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPException;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            SoapUtilities.handleUnauthorizedRequest(e);
        }
        System.out.println("TESTING: " + output);
        return output;
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
