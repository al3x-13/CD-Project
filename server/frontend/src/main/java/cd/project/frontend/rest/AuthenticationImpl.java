package cd.project.frontend.rest;

import cd.project.frontend.auth.AuthenticationHelpers;
import cd.project.frontend.rest.entities.UserCredentials;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationImpl implements Authentication {
    @GET
    @Path("/test")
    public String test() {
        System.out.println("TESTING");
        return "TESTING";
    }

    @POST
    @Path("/test2")
    public Response test2(String test) {
        System.out.println("TESTING2 - " + test);
        return Response.ok("TESTING2 - " + test).build();
    }

    @POST
    @Path("/authenticate")
    public Response authenticate(UserCredentials credentials) {
        System.out.println("CREDS: " + credentials);
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        if (username == null) {
            return Response.status(401).entity("Invalid credentials: username field not specified").build();
        }

        if (password == null) {
            return Response.status(401).entity("Invalid credentials: password field not specified").build();
        }

        String token = AuthenticationHelpers.authenticate(username, password);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return Response.ok(response).build();
    }

    @GET
    @Path("/logout")
    public Response invalidateSession(@HeaderParam("Authorization") String authHeader) {
        String token = authHeader;
        System.out.println("OUI: " + token);
        if (token == null) {
            return Response.status(401).entity("Missing 'Authorization' header").build();
        }

        if (!AuthenticationHelpers.invalidateSession(token)) {
            return Response.status(401).entity("Failed to invalidate session").build();
        }
        return Response.ok("Session invalidated").build();
    }
}
