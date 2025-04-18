package stdev.global.jwt.util.exception;


import stdev.global.infra.exception.auth.StdevAuthException;
import org.springframework.http.HttpStatus;

public class StdevJWTExpiredException extends StdevAuthException {
    public StdevJWTExpiredException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
