package cd.project.client.core;

import cd.project.client.Main;
import cd.project.frontend.rest.Authentication;
import cd.project.frontend.rest.entities.UserCredentials;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AuthenticationServiceRest {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static String authenticate(String username, String password) {
        ObjectMapper mapper = new ObjectMapper();
        UserCredentials credentials = new UserCredentials(username, password);

        try {
            String data = mapper.writeValueAsString(credentials);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/rest/auth/authenticate"
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() != 200) {
                return null;
            }

            System.out.println("OH YEAH BABY");
            JsonNode rootNode = mapper.readTree(res.body());
            return rootNode.path("token").asText();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean register(String username, String password) {
        ObjectMapper mapper = new ObjectMapper();
        UserCredentials credentials = new UserCredentials(username, password);

        try {
            String data = mapper.writeValueAsString(credentials);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/rest/auth/register"
                    ))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() != 200) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
