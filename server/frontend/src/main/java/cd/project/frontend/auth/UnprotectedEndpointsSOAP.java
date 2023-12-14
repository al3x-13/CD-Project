package cd.project.frontend.auth;

public enum UnprotectedEndpointsSOAP {
    HOME("/frontend/soap"),
    LOGIN("/frontend/soap/auth/login"),
    REGISTER("/frontend/soap/auth/register");

    private final String endpoint;

    UnprotectedEndpointsSOAP(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }
}
