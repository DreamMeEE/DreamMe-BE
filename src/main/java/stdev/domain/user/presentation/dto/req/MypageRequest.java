package stdev.domain.user.presentation.dto.req;

import java.time.LocalTime;

public record MypageRequest(

        LocalTime startTime,
        LocalTime endTime
) {
}
