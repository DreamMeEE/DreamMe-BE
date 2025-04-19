
package stdev.domain.other.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stdev.domain.other.application.OpenAILuckyService;
import stdev.domain.other.presentation.dto.request.LuckyGenerationRequest;
import stdev.domain.other.presentation.dto.response.LuckyGenerationResponse;

@RestController
@RequestMapping("/api/openai")
@RequiredArgsConstructor
@Slf4j
public class OpenAILuckyController {

    private final OpenAILuckyService openAILuckyService;
    @PostMapping("/lucky")
    public ResponseEntity<LuckyGenerationResponse> generateContent(@RequestBody LuckyGenerationRequest req) {
        return ResponseEntity.ok(openAILuckyService.generateLucky(req));
    }
}