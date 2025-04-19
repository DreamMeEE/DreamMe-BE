package stdev.domain.user.infra.exception;

import stdev.global.infra.exception.StDevException;
import org.springframework.http.HttpStatus;

public class EmailExistException extends StDevException {

    public EmailExistException() {
        super(HttpStatus.CONFLICT, "이메일이 존재합니다.");
    }
}
