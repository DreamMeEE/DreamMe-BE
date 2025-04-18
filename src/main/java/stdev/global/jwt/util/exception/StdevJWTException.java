package stdev.global.jwt.util.exception;

import stdev.global.infra.exception.auth.StdevAuthException;
import org.springframework.http.HttpStatus;

public class StdevJWTException extends StdevAuthException {
    public StdevJWTException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
