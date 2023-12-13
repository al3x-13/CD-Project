package cd.project.frontend.rest;

import cd.project.frontend.rest.entities.UserCredentials;
import jakarta.jws.WebService;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Response;

@WebService
public interface Authentication {
    String test();
    Response test2(String test);
    UserCredentials test3();
    String test4(UserCredentials credentials);
    Response authenticate(UserCredentials credentials);
    Response invalidateSession(@HeaderParam("Authorization") String authHeader);
}
