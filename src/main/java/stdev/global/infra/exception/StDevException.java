package stdev.global.infra.exception;

import org.springframework.http.HttpStatus;

public class StDevException extends RuntimeException {

    private final HttpStatus status;

    public StDevException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
