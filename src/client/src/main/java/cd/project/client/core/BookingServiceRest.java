package cd.project.client.core;

import cd.project.backend.domain.Lounge;
import cd.project.client.Main;
import cd.project.frontend.rest.entities.AvailableLoungesInput;
import cd.project.frontend.rest.entities.BookingAvailabilityInput;
import cd.project.frontend.rest.entities.CreateBookingInput;
import cd.project.frontend.rest.entities.UserCredentials;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookingServiceRest {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static String authToken = null;

    // TODO
    public static ArrayList<Lounge> getAvailableLounges(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime
    ) throws UnauthorizedException {
        ObjectMapper mapper = new ObjectMapper();
        AvailableLoungesInput input = new AvailableLoungesInput(
                beachId,
                date.toString(),
                fromTime.toString(),
                toTime.toString()
        );

        try {
            String data = mapper.writeValueAsString(input);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/rest/booking/getAvailableLounges"
                    ))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authToken)
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            RestUtilities.checkUnauthorizedStatus(res);
            if (res.statusCode() != 200) {
                return null;
            }

            return mapper.readValue(res.body(), new TypeReference<ArrayList<Lounge>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<Lounge> checkBookingAvailability(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals
    ) throws UnauthorizedException {
        ObjectMapper mapper = new ObjectMapper();
        BookingAvailabilityInput input = new BookingAvailabilityInput(
                beachId,
                date.toString(),
                fromTime.toString(),
                toTime.toString(),
                individuals
        );

        try {
            String data = mapper.writeValueAsString(input);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/rest/booking/checkBookingAvailability"
                    ))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authToken)
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            RestUtilities.checkUnauthorizedStatus(res);
            if (res.statusCode() != 200) {
                return null;
            }

            return mapper.readValue(res.body(), new TypeReference<ArrayList<Lounge>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    public static int createBooking(
            char beachId,
            LocalDate date,
            LocalTime fromTime,
            LocalTime toTime,
            int individuals
    ) throws UnauthorizedException {
        ObjectMapper mapper = new ObjectMapper();
        CreateBookingInput input = new CreateBookingInput(
                beachId,
                date.toString(),
                fromTime.toString(),
                toTime.toString(),
                individuals
        );

        try {
            String data = mapper.writeValueAsString(input);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/rest/booking/createBooking"
                    ))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authToken)
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            RestUtilities.checkUnauthorizedStatus(res);
            if (res.statusCode() != 200) {
                return -1;
            }

            return Integer.parseInt(res.body().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean cancelBooking(int bookingId) throws UnauthorizedException {
        try {
            String data = String.valueOf(bookingId);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "http://" + Main.serverAddress + ":" + Main.serverPort + "/frontend/rest/booking/cancelBooking"
                    ))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authToken)
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            RestUtilities.checkUnauthorizedStatus(res);
            if (res.statusCode() != 200) {
                return false;
            }

            return Boolean.parseBoolean(res.body().trim());
        } catch (Exception e) {
            return false;
        }
    }

    public static void setAuthToken(String token) {
        authToken = "Bearer " + token;
    }

    public static void resetAuthHeader() {
        authToken = null;
    }
}
