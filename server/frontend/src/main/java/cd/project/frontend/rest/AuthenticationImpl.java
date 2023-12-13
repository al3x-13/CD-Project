package cd.project.frontend.rest;

import cd.project.frontend.auth.AuthenticationHelpers;
import cd.project.frontend.rest.entities.UserCredentials;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    @Path("/register")
    public Response register(UserCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        if (!AuthenticationHelpers.usernameIsAvailable(username)) {
            return Response.status(409).entity("Username already exists").build();
        }

        if (!AuthenticationHelpers.register(username, password)) {
            return Response.status(500).entity("Failed to create user account").build();
        }
        return Response.ok("Account created successfully").build();
    }

    @POST
    @Path("/authenticate")
    public Response authenticate(UserCredentials credentials) {
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
}
