package cd.project.frontend.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface Authentication {
    @WebMethod
    String authenticate(String username, String password);

    @WebMethod
    boolean invalidateSession();

    @WebMethod
    boolean register();
}
