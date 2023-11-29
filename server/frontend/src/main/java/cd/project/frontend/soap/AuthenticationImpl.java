package cd.project.frontend.soap;

import cd.project.frontend.auth.AuthenticationHelpers;
import jakarta.jws.WebService;

@WebService(endpointInterface = "cd.project.frontend.soap.Authentication")
public class AuthenticationImpl implements Authentication {
    @Override
    public String authenticate(String username, String password) {
        System.out.println("Username input: " + username);
        System.out.println("Password input: " + password);
        return AuthenticationHelpers.authenticate(username, password);
    }

    @Override
    public boolean register() {
        return false;
    }
}
