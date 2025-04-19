package stdev.domain.dreamanalysis.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record ContentResponse(
        String comment,

        String imageUrl,

        String headImage,

        String headComment
) {
    public static ContentResponse of(String comment,

                                     String imageUrl,

                                     String headImage,

                                     String headComment) {
        return new ContentResponse(comment, imageUrl, headImage, headComment);
    }
}