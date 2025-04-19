package stdev.domain.other.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import stdev.domain.other.application.OpenAILuckyService;
import stdev.domain.openai.presentation.dto.request.OpenAITextRequest;
import stdev.domain.other.domain.entity.Lucky;
import stdev.domain.other.domain.repository.LuckyRepository;
import stdev.domain.other.presentation.dto.request.LuckyGenerationRequest;
import stdev.domain.other.presentation.dto.response.LuckyGenerationResponse;
import stdev.domain.openai.presentation.dto.response.OpenAITextResponse;
import stdev.domain.user.infra.exception.UserNotFoundException;
import stdev.global.infra.feignclient.OpenAITextFeignClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OpenAILuckyServiceImpl implements OpenAILuckyService {

    private final OpenAITextFeignClient openAITextFeignClient;

    private final LuckyRepository luckyRepository;
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.text.model}")
    private String textModel;

    @Override
    public LuckyGenerationResponse generateLucky(LuckyGenerationRequest req) {


        String dreamCategory = extracted(req);

        Lucky lucky = luckyRepository.findByKeyword(req.luckyCategory() + dreamCategory).orElse(null);

        if (lucky == null) {
            throw new UserNotFoundException("엥 해당 럭키가 없어...");
        }


        try {
            // 프롬프트 생성 함수를 호출
            String prompt = createPromptForTopic(req);

            List<OpenAITextRequest.Message> messages = new ArrayList<>();
            messages.add(OpenAITextRequest.Message.builder()
                    .role("user")
                    .content(prompt)
                    .build());

            OpenAITextRequest request = OpenAITextRequest.builder()
                    .model(textModel)
                    .messages(messages)
                    .temperature(0.7)
                    .maxTokens(300)
                    .build();

            OpenAITextResponse response = openAITextFeignClient.generateText(
                    "Bearer " + apiKey,
                    request
            );
            String and = "\n😊/😨 운세에 대해서 알아볼까요?\n";
            if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                String content = response.getChoices().get(0).getMessage().getContent();
                log.info("Generated text: {}", content.substring(0, Math.min(content.length(), 100)) + "...");
                return LuckyGenerationResponse.of(content + and + lucky.getComment(), lucky.getLuckyImage());

            } else {
                throw new UserNotFoundException("내용을 생성할 수 없습니다.");
            }
        } catch (Exception e) {
            log.error("Error generating text: {}", e.getMessage(), e);
            throw new RuntimeException("텍스트 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private static String extracted(LuckyGenerationRequest req) {
        String category = req.dreamCategory();
        String dream = "";

        // 허몽(虛夢) - 의미 없는 헛된 꿈: 재난, 횡재
        if(category.equals("재난") || category.equals("횡재")) {
            dream = "허몽";
        }
        // 잡몽(雜夢) - 일상 경험이나 생각이 뒤섞인 잡다한 꿈: 가족, 친구, 기타
        else if(category.equals("가족") || category.equals("친구") || category.equals("기타")) {
            dream = "잡몽";
        }
        // 심몽(心夢) - 마음의 상태나 욕망이 반영된 꿈: 사랑, 돈
        else if(category.equals("사랑") || category.equals("돈")) {
            dream = "심몽";
        }
        // 영몽(靈夢) - 초자연적인 영적 메시지가 담긴 꿈: 동물, 건강
        else if(category.equals("동물") || category.equals("건강")) {
            dream = "영몽";
        }
        // 정몽(正夢) - 미래를 예시하는 예지적 꿈: 악몽, 도망침
        else if(category.equals("악몽") || category.equals("도망침")) {
            dream = "정몽";
        }
        else {
           throw new UserNotFoundException("없는 꿈 종류ㅜ...");
        }

        return dream;
    }

    // 토픽에 맞는 프롬프트를 생성하는 함수
    private String createPromptForTopic(LuckyGenerationRequest req) {

        return String.format(
                "Then structure your response in this exact format with emojis:\n" +
                        "\n" +
                        "🔍 꿈에 대해서 알아볼까요?:\n" +
                        "\n" + // 여기에 줄바꿈 추가
                        "Please analyze the following dream: \\\"%s\\\".\\n\" + Your response must be written in Korean and should be between 70 and 100 characters.\",\n",
                req.topic()
        );
    }
}
