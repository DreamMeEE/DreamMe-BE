package stdev.global.infra.exception.auth;

import stdev.global.infra.exception.StDevException;
import org.springframework.http.HttpStatus;

public class StdevAuthException extends StDevException {
    public StdevAuthException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}