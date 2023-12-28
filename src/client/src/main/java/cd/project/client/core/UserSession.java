package cd.project.client.core;

import cd.project.client.CommunicationProtocol;
import cd.project.client.Main;
import org.mindrot.jbcrypt.BCrypt;

public class UserSession {
    private static String username;
    private static String passwordHash;
    private static String sessionToken = null;
    private static final String testUsername = "test1337";
    private static final String testPasswordHash = BCrypt.hashpw("testing", BCrypt.gensalt());

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        username = username;
    }

    public static void setPasswordHash(String passwordHash) {
        passwordHash = passwordHash;
    }

    // TODO: handle 401s (particularly when session token expires)

    public static boolean authenticate(String usernameInput, String passwordInput) {
        if (Main.clientProtocol == CommunicationProtocol.SOAP) {
            String token = UserSessionSoapHelpers.authenticate(usernameInput, passwordInput);
            if (token == null) {
                return false;
            }
            UserSessionSoapHelpers.setAuthHeader(token);
            username = usernameInput;
            sessionToken = token;
            return true;
        }
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
            UserSessionSoapHelpers.resetAuthHeader();
        } else {
            // TODO
        }
        sessionToken = null;
    }
}
