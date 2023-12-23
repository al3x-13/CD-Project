package cd.project.frontend.soap;

import cd.project.frontend.auth.AuthenticationHelpers;
import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;

@WebService(endpointInterface = "cd.project.frontend.soap.Authentication", serviceName = "Authentication")
public class AuthenticationImpl implements Authentication {
    @Resource
    private WebServiceContext webServiceContext;

    @Override
    public String authenticate(String username, String password) {
        return AuthenticationHelpers.authenticate(username, password);
    }

    @Override
    public boolean register(String username, String password) {
        if (!AuthenticationHelpers.usernameIsAvailable(username)) {
            return false;
        }
        return AuthenticationHelpers.register(username, password);
    }

    @Override
    public String test() {
        return "DEEZ NUTZ";
    }
}
