package stdev.domain.dreamdiary.infra.exception;

import org.springframework.http.HttpStatus;
import stdev.global.infra.exception.StDevException;

public class InfromationDiaryException2  extends StDevException {
    public InfromationDiaryException2() {
        super(HttpStatus.valueOf(412), "이미 해당 날짜에 정보가 존재합니다. 수정이 불가능합니다.");
    }
}
