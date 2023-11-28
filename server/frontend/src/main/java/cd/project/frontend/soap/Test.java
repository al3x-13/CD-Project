package cd.project.frontend.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface Test {
    @WebMethod
    String test(int id);
}
