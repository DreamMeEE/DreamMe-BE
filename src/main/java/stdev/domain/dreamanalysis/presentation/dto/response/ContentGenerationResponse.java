package stdev.domain.dreamanalysis.presentation.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentGenerationResponse {
    private String text;
    private String imageUrl;
}