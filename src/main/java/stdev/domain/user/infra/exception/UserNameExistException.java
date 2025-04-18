package stdev.domain.user.infra.exception;

import stdev.global.infra.exception.StDevException;
import org.springframework.http.HttpStatus;


public class UserNameExistException extends StDevException {

    public UserNameExistException() {
        super(HttpStatus.CONFLICT, "아이디가 이미 존재합니다.");
    }
}
