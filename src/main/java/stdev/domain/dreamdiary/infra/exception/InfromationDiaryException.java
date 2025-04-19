package stdev.domain.dreamdiary.infra.exception;

import org.springframework.http.HttpStatus;
import stdev.global.infra.exception.StDevException;

public class InfromationDiaryException extends StDevException {

    public InfromationDiaryException() {
        super(HttpStatus.valueOf(412), "이미 해당 날짜에 정보가 존재합니다. 이전 날짜를 수정하거나 삭제해주세요.");
    }
}
