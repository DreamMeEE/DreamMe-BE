package stdev.domain.dreamdiary.presentation.dto.request;

import java.time.LocalDateTime;

public record DiaryPatchRequest(

        Long id,
        LocalDateTime sleepStart,

        LocalDateTime sleepEnd,


        // 수면 메모
        String note,

        String rate,

        String title,

        String content,

        // 꿈 카테고리
        String diaryCategory

        ) {

}
