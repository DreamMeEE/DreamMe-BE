package stdev.domain.dreamanalysis.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record DreamRecordRequest(
        String comment,

        MultipartFile image
) {
}
