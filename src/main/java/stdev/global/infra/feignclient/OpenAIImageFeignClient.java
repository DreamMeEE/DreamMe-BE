package stdev.global.infra.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import stdev.domain.openai.presentation.dto.request.OpenAIImageRequest;
import stdev.domain.openai.presentation.dto.response.OpenAIImageResponse;

@FeignClient(
        name = "OpenAIImageAPI",
        url = "${openai.api.url}"
)
public interface OpenAIImageFeignClient {
    @PostMapping(value = "/v1/images/generations")
    OpenAIImageResponse generateImage(
            @RequestHeader("Authorization") String apiKey,
            @RequestBody OpenAIImageRequest request
    );
}