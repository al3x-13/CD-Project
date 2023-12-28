package cd.project.client.core;

import jakarta.xml.ws.WebServiceException;
import org.apache.cxf.transport.http.HTTPException;

public class SoapUtilities {
    public static void handleUnauthorizedRequest(Exception e) throws UnauthorizedException {
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
}
