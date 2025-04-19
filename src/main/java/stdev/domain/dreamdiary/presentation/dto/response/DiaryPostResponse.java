package stdev.domain.dreamdiary.presentation.dto.response;

import java.time.LocalDateTime;

public record DiaryPostResponse(
        Long id // 기록 아이디

) {
    public static DiaryPostResponse of(Long id) {
        return new DiaryPostResponse(id);
    }

}
