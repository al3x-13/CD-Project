package cd.project.client.core;

import org.mindrot.jbcrypt.BCrypt;

public class UserSession {
    private static String username;
    private static String passwordHash;
    private static String sessionToken = null;
    private static final String testUsername = "test";
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

    public static boolean authenticate(String username, String password) {
        // TODO
        if (!username.equals(testUsername) || !BCrypt.checkpw(password, testPasswordHash)) {
            return false;
        }
        username = username;
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
}
