package cd.project.frontend.rest.entities;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "UserCredentials")
public class UserCredentials {
    private String username;
    private String password;

    public UserCredentials() {}

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.username + ":" + this.password;
    }
}
