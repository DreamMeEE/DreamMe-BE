package stdev.domain.user.infra.exception;

import stdev.global.infra.exception.StDevException;
import org.springframework.http.HttpStatus;

public class PasswordNotCorrectException extends StDevException {
    public PasswordNotCorrectException() {
        super(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }
}
