package cd.project.client.core;

import cd.project.client.Main;
import cd.project.client.Router;
import jakarta.xml.ws.WebServiceException;
import org.apache.cxf.transport.http.HTTPException;

public class SoapUtilities {
    public static void checkUnauthorizedStatus(Exception e) throws UnauthorizedException {
        if (e instanceof WebServiceException) {
            Throwable cause = e.getCause();
            if (cause instanceof HTTPException) {
                HTTPException httpException = (HTTPException) cause;
                if (httpException.getResponseCode() == 401) {
                    throw new UnauthorizedException("Unauthorized Request", cause);
                }
            }
        }
    }

    public static void handleExpiredSession() {
        UserSession.invalidateSession();
        Main.sessionExpiredNotification = true;
        Router.navigateToLogin();
    }
}
