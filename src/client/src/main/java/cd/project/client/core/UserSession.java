package cd.project.client.core;

import cd.project.client.CommunicationProtocol;
import cd.project.client.Main;
import org.mindrot.jbcrypt.BCrypt;

public class UserSession {
    private static String username;
    private static String sessionToken = null;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        username = username;
    }

    public static boolean authenticate(String usernameInput, String passwordInput) {
        if (Main.clientProtocol == CommunicationProtocol.SOAP) {
            String token = AuthenticationServiceSoap.authenticate(usernameInput, passwordInput);
            if (token == null) {
                return false;
            }
            AuthenticationServiceSoap.setAuthHeader(token);
            BookingServiceSoap.setAuthHeader(token);
            username = usernameInput;
            sessionToken = token;
            return true;
        }
        // TODO: implement REST
        return false;
    }

    public static boolean register(String username, String password) {
        if (Main.clientProtocol == CommunicationProtocol.SOAP) {
            return AuthenticationServiceSoap.register(username, password);
        }

        // TODO: implement REST
        return false;
    }

    public static String getSessionToken() {
        return sessionToken;
    }

    public static void setSessionToken(String token) {
        sessionToken = token;
    }

    public static boolean isAuthenticated() {
        return sessionToken != null;
    }

    public static void invalidateSession() {
        if (Main.clientProtocol == CommunicationProtocol.SOAP) {
            AuthenticationServiceSoap.resetAuthHeader();
            BookingServiceSoap.resetAuthHeader();
        } else {
            // TODO
        }
        sessionToken = null;
    }
}
