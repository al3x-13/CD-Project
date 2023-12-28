package cd.project.client.core;

import cd.project.client.Main;
import cd.project.frontend.soap.Authentication;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSessionSoapHelpers {
    // SOAP setup
    private static Authentication authService = instantiateAuthService();
    private static Map<String, Object> authServiceCtx;

    private static Authentication instantiateAuthService() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/soap/auth");
        factory.setServiceClass(Authentication.class);
        Authentication authService = (Authentication) factory.create();
        authServiceCtx = ((BindingProvider) authService).getRequestContext();
        return authService;
    }

    public static String authenticate(String username, String password) {
        return authService.authenticate(username, password);
    }

    public static void setAuthHeader(String token) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Collections.singletonList("Bearer " + token));
        authServiceCtx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }

    public static void resetAuthHeader() {
        authServiceCtx.clear();
    }
}
