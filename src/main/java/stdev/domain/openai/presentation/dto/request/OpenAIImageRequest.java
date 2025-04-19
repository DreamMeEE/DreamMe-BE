package stdev.domain.openai.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpenAIImageRequest {
    private String model;
    private String prompt;
    private int n;
    private String size;
}