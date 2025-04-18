package stdev.domain.oauth2.infra.exception;

import stdev.global.infra.exception.auth.StdevAuthException;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends StdevAuthException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED, "Refresh Token이 유효하지 않습니다.");
    }
}
