package cd.project.frontend.auth;

public enum UnprotectedEndpointsREST {
    HOME("/frontend/rest"),
    LOGIN("/frontend/rest/auth/authenticate"),
    REGISTER("/frontend/rest/auth/register");

    private final String endpoint;

    UnprotectedEndpointsREST(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }
}
