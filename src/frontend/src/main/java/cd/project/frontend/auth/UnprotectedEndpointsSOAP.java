package cd.project.frontend.auth;

public enum UnprotectedEndpointsSOAP {
    HOME("/frontend/soap"),
    AUTH("/frontend/soap/auth"),
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
