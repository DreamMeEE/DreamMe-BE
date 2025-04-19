package stdev.domain.dreamdiary.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public record DiaryPostRequest(
        LocalDateTime sleepStart,

        LocalDateTime sleepEnd,


        // 수면 메모
        String note,

        String rate,

        String title,

        String content,

        // 꿈 카테고리
        String diaryCategory,



        String comment,

        String imageUrl,

        String dreamCategory,

        boolean flag

        ) {

}
