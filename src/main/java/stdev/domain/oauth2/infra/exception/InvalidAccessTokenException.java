package stdev.domain.oauth2.infra.exception;

import stdev.global.infra.exception.auth.StdevAuthException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends StdevAuthException {
    public InvalidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Access Token이 유효하지 않습니다.");
    }
}
