package lu.dans.insideapp.controllers.errors;

public class UnauthorizedUserError extends RuntimeException {
    public UnauthorizedUserError() {
        super();
    }

    public UnauthorizedUserError(String message) {
        super(message);
    }
}
