package cd.project.frontend.soap;

import cd.project.frontend.auth.AuthenticationHelpers;
import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;

@WebService(endpointInterface = "cd.project.frontend.soap.Authentication")
public class AuthenticationImpl implements Authentication {
    @Resource
    private WebServiceContext webServiceContext;

    @Override
    public String authenticate(String username, String password) {
        // TODO: remove this
        System.out.println("Username input: " + username);
        System.out.println("Password input: " + password);
        return AuthenticationHelpers.authenticate(username, password);
    }

    @Override
    public boolean register() {
        return false;
    }
}
