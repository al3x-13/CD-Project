package cd.project.client.core;

public class UnauthorizedException extends Exception {
    UnauthorizedException(String message) {
        super(message);
    }

    UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
