package stdev.domain.user.infra.exception;


import stdev.global.infra.exception.auth.StdevAuthException;
import org.springframework.http.HttpStatus;

public class InvalidRoleException extends StdevAuthException {
    public InvalidRoleException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
