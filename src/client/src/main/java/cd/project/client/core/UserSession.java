package cd.project.client.core;

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

    public static boolean authenticate(String usernameInput, String passwordInput) {
        // TODO
        if (!usernameInput.equals(testUsername) || !BCrypt.checkpw(passwordInput, testPasswordHash)) {
            return false;
        }
        username = usernameInput;
        passwordHash = passwordHash;
        sessionToken = "test";
        return true;
    }

    public static String getSessionToken() {
        return sessionToken;
    }

    public static void setSessionToken(String sessionToken) {
        sessionToken = sessionToken;
    }

    public static boolean isAuthenticated() {
        return sessionToken != null;
    }

    public static void invalidateSession() {
        sessionToken = null;
    }
}
