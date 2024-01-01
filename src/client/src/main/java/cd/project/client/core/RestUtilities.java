package cd.project.client.core;

import cd.project.client.Main;
import cd.project.client.Router;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class RestUtilities {
    public static void checkUnauthorizedStatus(HttpResponse response) throws UnauthorizedException {
        if (response.statusCode() == 401) {
            throw new UnauthorizedException("Unauthorized Request");
        }
    }

    public static void handleExpiredSession() {
        UserSession.invalidateSession();
        Main.sessionExpiredNotification = true;
        Router.navigateToLogin();
    }
}
