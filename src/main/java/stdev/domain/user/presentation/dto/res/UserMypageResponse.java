package stdev.domain.user.presentation.dto.res;

import java.time.LocalTime;

public record UserMypageResponse(

        String name,

        String profile,

        int recordCnt,

        int dreamCnt,

        float rate,

        LocalTime startTime,

        LocalTime endTime,

       String time
) {
    public static UserMypageResponse of(String name,

                                        String profile,

                                        int recordCnt,

                                        int dreamCnt,

                                        float rate,

                                        LocalTime startTime,

                                        LocalTime endTime,

                                        String time) {
        return new UserMypageResponse(name, profile, recordCnt, dreamCnt, rate, startTime, endTime, time);
    }
}
