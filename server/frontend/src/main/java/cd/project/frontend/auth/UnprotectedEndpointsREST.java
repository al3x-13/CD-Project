package cd.project.frontend.auth;

public enum UnprotectedEndpointsREST {
    HOME("/rest"),
    LOGIN("/rest/auth/login"),
    REGISTER("/rest/auth/register");

    private final String endpoint;

    UnprotectedEndpointsREST(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }
}
