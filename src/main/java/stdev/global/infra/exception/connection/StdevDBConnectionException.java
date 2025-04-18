package stdev.global.infra.exception.connection;

import stdev.global.infra.exception.StDevException;
import org.springframework.http.HttpStatus;

public class StdevDBConnectionException extends StDevException {
    public StdevDBConnectionException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "DB Connection 과정 중 문제가 발생했습니다.");
    }
}
