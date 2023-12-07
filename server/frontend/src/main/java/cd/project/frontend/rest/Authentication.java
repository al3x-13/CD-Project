package cd.project.frontend.rest;

import cd.project.frontend.rest.entities.UserCredentials;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface Authentication {
    String test();
    Response authenticate(UserCredentials credentials);
    Response invalidateSession(@HeaderParam("Authorization") String authHeader);
}
