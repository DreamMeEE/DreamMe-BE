package stdev.domain.oauth2.infra.exception;

import stdev.global.infra.exception.auth.StdevAuthException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotExistException extends StdevAuthException {
    public RefreshTokenNotExistException() {
        super(HttpStatus.UNAUTHORIZED, "Refresh Token이 DB에 존재하지 않습니다.");
    }
}
