package stdev.domain.openai.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenAIImageResponse {
    private long created;
    private List<ImageData> data;

    @Getter
    @Setter
    public static class ImageData {
        private String url;

        @JsonProperty("b64_json")
        private String b64Json;

        @JsonProperty("revised_prompt")
        private String revisedPrompt;
    }
}