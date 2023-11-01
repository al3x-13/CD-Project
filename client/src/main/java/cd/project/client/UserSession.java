package cd.project.client;

import org.mindrot.jbcrypt.BCrypt;

public class UserSession {
    private String username;
    private String passwordHash;
    private String sessionToken = null;
    private final String testUsername = "test";
    private final String testPasswordHash = BCrypt.hashpw("testing", BCrypt.gensalt());

    public UserSession() {}

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean authenticate(String username, String password) {
        // TODO
        if (!username.equals(this.testUsername) || !BCrypt.checkpw(password, this.testPasswordHash)) {
            return false;
        }
        this.username = username;
        this.passwordHash = passwordHash;
        this.sessionToken = "test";
        return true;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public boolean validSession() {
        // TODO
        return this.sessionToken != null;
    }
}
