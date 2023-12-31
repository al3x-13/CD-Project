package cd.project.frontend.auth;

import cd.project.frontend.database.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.jws.WebMethod;
import jakarta.xml.ws.handler.MessageContext;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationHelpers {
    private static final int sessionExpireMinutes = 360;

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
        ResultSet data = DbConnection.executeQuery("SELECT id, password_hash FROM users WHERE username = ?", username);
        boolean validPassword = false;
        int userId = -1;

        try {
            if (!data.next()) return null;
            validPassword = BCrypt.checkpw(password, data.getString("password_hash"));
            userId = data.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!validPassword) return null;

        // generate new jwt token
        return JwtHelper.createToken(username, userId);
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
        ResultSet data = DbConnection.executeQuery("SELECT COUNT(*) as count FROM users WHERE username = ?", username);
        if (data == null) return false;

        try {
            data.next();
            return data.getInt("count") == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns whether an endpoint is protected, i.e. requires authentication.
     * @param endpoint endpoint
     * @return Whether the endpoint is protected
     */
    public static boolean endpointIsProtected(String endpoint) {
        boolean protectedEndpoint = true;
        // Home page endpoint
        if (endpoint.equals("/frontend") || endpoint.equals("/frontend/")) {
            protectedEndpoint = false;
        }

        // Protected SOAP endpoints
        for (UnprotectedEndpointsSOAP value : UnprotectedEndpointsSOAP.values()) {
            if (value.getEndpoint().equals(endpoint)) {
                protectedEndpoint = false;
                break;
            }
        }
        // Protected REST endopints
        for (UnprotectedEndpointsREST value : UnprotectedEndpointsREST.values()) {
            if (value.getEndpoint().equals(endpoint)) {
                protectedEndpoint = false;
                break;
            }
        }
        return protectedEndpoint;
    }

    /**
     * Gets session token from authorization header if it exists.
     * @param messageContext jakarta message context
     * @return session token or null
     */
    public static String getSessionTokenFromMessageContext(MessageContext messageContext) {
        Map<String, List<String>> headers = (Map<String, List<String>>) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        List<String> authHeader = headers.get("Authorization");
        System.out.println("HEADERS: " + headers);
        if (authHeader == null) return null;
        return headers.get("Authorization").get(0).split(" ")[1];
    }
}
