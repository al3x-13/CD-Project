package cd.project.frontend.rest;

import cd.project.frontend.rest.entities.UserCredentials;
import jakarta.jws.WebService;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Response;

public interface Authentication {
    String test();
    Response register(UserCredentials credentials);
    Response authenticate(UserCredentials credentials);
}
