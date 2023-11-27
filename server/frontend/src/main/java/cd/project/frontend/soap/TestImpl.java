package cd.project.frontend.soap;

import jakarta.jws.WebService;

@WebService(endpointInterface = "cd.project.frontend.soap.Test")
public class TestImpl implements Test {
    @Override
    public int test() {
        return 69;
    }
}
