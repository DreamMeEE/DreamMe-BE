package stdev.global.infra.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import stdev.domain.openai.presentation.dto.request.OpenAITextRequest;
import stdev.domain.openai.presentation.dto.response.OpenAITextResponse;

@FeignClient(
        name = "OpenAITextAPI",
        url = "${openai.api.url}"
)
public interface OpenAITextFeignClient {
    @PostMapping(value = "/v1/chat/completions")
    OpenAITextResponse generateText(
            @RequestHeader("Authorization") String apiKey,
            @RequestBody OpenAITextRequest request
    );
}