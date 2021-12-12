package lu.dans.insideapp.controllers;

import lu.dans.insideapp.controllers.errors.UnauthorizedUserError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UnauthorizedUserError.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public UnauthorizedUserError handleJwtUnauthorizedUserException(UnauthorizedUserError e) {
        return e;
    }
}
