package cd.project.frontend.auth;

public enum UnprotectedEndpointsSOAP {
    HOME("/soap"),
    LOGIN("/soap/auth/login"),
    REGISTER("/soap/auth/register");

    private final String endpoint;

    UnprotectedEndpointsSOAP(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }
}
