package stdev.domain.dreamdiary.presentation.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record DiaryGetResponse(


        Long id,
        LocalTime sleepStart,

        LocalTime sleepEnd,
        // 수면 메모
        String note,

        String rate,

        String title,

        String content,

        // 꿈 카테고리
        String diaryCategory,

        // 전체 수면시간
        String time,



        // 꿈 분석이 있는지 없는지
        boolean flag

) {
    public static DiaryGetResponse of(Long id,
                                      LocalTime sleepStart,

                                      LocalTime sleepEnd,

                                      // 수면 메모
                                      String note,

                                      String rate,

                                      String title,

                                      String content,

                                      String diaryCategory,

                                      String time,

                                      boolean flag) {
        return new DiaryGetResponse(id, sleepStart, sleepEnd, note, rate, title, content, diaryCategory, time,flag);
    }

}
