package stdev.domain.oauth2.infra.exception;


import stdev.global.infra.exception.auth.StdevAuthException;
import org.springframework.http.HttpStatus;

public class DuplicateLoginException extends StdevAuthException {
    public DuplicateLoginException() {
        super(HttpStatus.UNAUTHORIZED, "중복 로그인은 허용되지 않습니다.");
    }
}
