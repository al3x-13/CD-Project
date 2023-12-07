package cd.project.frontend.rest.entities;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserCredentials")
public class UserCredentials {
    private String username;
    private String password;

    public UserCredentials() {}

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
}
