package cd.project.frontend.auth;

import cd.project.frontend.database.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationHelpers {
    /**
     * Register a new user.
     * @param username username
     * @param password password
     * @return Whether user registration was successful
     */
    public static boolean register(String username, String password) {
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        return DbConnection.executeUpdate(
                "INSERT INTO users (username, password_hash) VALUES (?, ?)",
                username,
                passwordHash
        ) == 1;
    }

    /**
     * Authenticates a user and generates session token if the credentials are valid, otherwise returns null.
     * @param username username
     * @param password password
     * @return User session token or null
     */
    public static String authenticate(String username, String password) {
        ResultSet data = DbConnection.executeQuery("SELECT password_hash FROM users WHERE username = ?", username);
        boolean validPassword = false;

        try {
            if (!data.next()) return null;
            validPassword = BCrypt.checkpw(password, data.getString("password_hash"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!validPassword) return null;

        // generating new session token
        String newSessionToken = UUID.randomUUID().toString();
        return DbConnection.executeUpdate(
                "UPDATE users SET session_token = ? WHERE username = ?",
                newSessionToken,
                username
        ) == 1 ? newSessionToken : null;
    }

    /**
     * Invalidates a user session.
     * @param username username
     * @return Whether user session was invalidated successfully
     */
    public static boolean invalidateSession(String username) {
        return DbConnection.executeUpdate("UPDATE users SET session_token = null WHERE username = ?", username) == 1;
    }

    /**
     * Deletes a user account.
     * @param username username
     * @return Whether user account was successfully deleted
     */
    public static boolean deleteAccount(String username) {
        return DbConnection.executeUpdate("DELETE FROM users WHERE username = ?", username) == 1;
    }

    /**
     * Checks if a username is available to be taken.
     * @param username username
     * @return Whether username is available
     */
    public static boolean usernameIsAvailable(String username) {
        ResultSet data = DbConnection.executeQuery("SELECT * FROM users WHERE username = ?", username);
        if (data == null) return false;

        try {
            data.next();
            System.out.println("ROW COUNT: " + data.getInt(1));
            return data.getInt(1) == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean endpointIsProtected(String endpoint) {
        boolean protectedEndpoint = true;
        for (UnprotectedEndpointsSOAP value : UnprotectedEndpointsSOAP.values()) {
            if (value.getEndpoint().equals(endpoint)) protectedEndpoint = false;
        }
        return protectedEndpoint;
    }
}
