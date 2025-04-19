package stdev.domain.dreamanalysis.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stdev.domain.dreamanalysis.application.OpenAIContentService;
import stdev.domain.dreamanalysis.domain.entity.DreamAnalysis;
import stdev.domain.dreamanalysis.domain.repository.DreamAnalysisRepository;
import stdev.domain.dreamanalysis.presentation.dto.response.*;
import stdev.domain.dreamdiary.domain.repository.DreamDiaryRepository;
import stdev.domain.openai.presentation.dto.request.OpenAIImageRequest;
import stdev.domain.openai.presentation.dto.request.OpenAITextRequest;
import stdev.domain.openai.presentation.dto.response.OpenAIImageResponse;
import stdev.domain.openai.presentation.dto.response.OpenAITextResponse;
import stdev.domain.other.domain.entity.Head;
import stdev.domain.other.domain.repository.HeadRepository;
import stdev.domain.record.domain.entity.Record;
import stdev.domain.record.domain.repository.RecordRepository;
import stdev.domain.user.infra.exception.UserNotFoundException;
import stdev.global.config.FileStore;
import stdev.global.infra.feignclient.OpenAIImageFeignClient;
import stdev.global.infra.feignclient.OpenAITextFeignClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OpenAIContentServiceImpl implements OpenAIContentService {

    private final OpenAITextFeignClient openAITextFeignClient;
    private final OpenAIImageFeignClient openAIImageFeignClient;
    private final HeadRepository headRepository;
    private final RecordRepository recordRepository;
    private final DreamAnalysisRepository dreamAnalysisRepository;
    private final DreamDiaryRepository dreamDiaryRepository;

    private final FileStore fileStore;
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.text.model}")
    private String textModel;

    @Value("${openai.image.model}")
    private String imageModel;

    @Override
    public CompletableFuture<ContentGenerationResponse> generateContent(String topic) {
        return null;
    }

    @Override
    public DreamCommentResponse generateComment(String topic) {
        try {
            // 텍스트 생성 프롬프트 설정
            String prompt = createTextPrompt(topic);

            List<OpenAITextRequest.Message> messages = new ArrayList<>();
            messages.add(OpenAITextRequest.Message.builder()
                    .role("system")
                    .content("You are a dream interpretation expert. Based on psychological knowledge, please analyze the symbols and meanings that appear in the dream.")
                    .build());
            messages.add(OpenAITextRequest.Message.builder()
                    .role("user")
                    .content(prompt)
                    .build());

            OpenAITextRequest request = OpenAITextRequest.builder()
                    .model(textModel)
                    .messages(messages)
                    .temperature(0.7)
                    .maxTokens(500)
                    .build();

            OpenAITextResponse response = openAITextFeignClient.generateText(
                    "Bearer " + apiKey,
                    request
            );

            if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                String content = response.getChoices().get(0).getMessage().getContent();
                log.info("Generated text: {}", content);
                String category = content.substring(0, 2);
                String substring = content.substring(4);

                String comment = substring.trim();
                return DreamCommentResponse.of(comment, category, true);
            } else {
                throw new UserNotFoundException("내용을 생성할 수 없습니다.");
            }
        } catch (Exception e) {
            log.error("Error generating text: {}", e.getMessage(), e);
            throw new UserNotFoundException("오류 발생!!!");
        }
    }

    @Override
    public DreamImageResponse generateImage(String topic) {
        try {
            // 이미지 생성 프롬프트 설정
            String imagePrompt = createImagePrompt(topic);

            OpenAIImageRequest request = OpenAIImageRequest.builder()
                    .model(imageModel)
                    .prompt(imagePrompt)
                    .n(1)
                    .size("1024x1024")
                    .build();

            OpenAIImageResponse response = openAIImageFeignClient.generateImage(
                    "Bearer " + apiKey,
                    request
            );

            if (response.getData() != null && !response.getData().isEmpty()) {
                String imageUrl = response.getData().get(0).getUrl();
                log.info("Generated image URL: {}", imageUrl);

                MultipartFile multipartFile = convertImageUrlToMultipartFile(imageUrl);
                String imageUrlF = fileStore.storeFile(multipartFile);

                return DreamImageResponse.of(imageUrlF);
            } else {
                log.error("No image data returned from OpenAI API");
                return null;
            }
        } catch (Exception e) {
            log.error("Error generating image: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public HeadResponse generateHead(String category) {
        log.info(category);
        Head head = headRepository.findByHeadCategory(category).orElse(null);
        if (head == null) {
            throw new UserNotFoundException("뇌 정보가 없다");
        }
        return HeadResponse.of(head.getHeadImage(), head.getHeadContent());
    }

    @Override
    public ContentResponse getContent(Long id, String userId) {
        Record record = recordRepository.findById(id).orElse(null);
        if (record == null) {
            throw new UserNotFoundException("기록이 없습니다.");
        }
        DreamAnalysis dreamAnalysis = record.getDreamAnalysis();
        String dreamCategory = dreamAnalysis.getDreamCategory();
        Head head = headRepository.findByHeadCategory(dreamCategory).orElse(null);
        if (head == null) {
            throw new UserNotFoundException("해당 뇌 데이터가 없습니다.");
        }

        return ContentResponse.of(dreamAnalysis.getDreamComment(),
                dreamAnalysis.getDreamImageUrl(), head.getHeadImage(), head.getHeadContent());

    }


    private String createTextPrompt(String topic) {

        return String.format(
                "Please analyze the following dream: \"%s\".\n" +
                        "Start your response with **either** '잡몽:' or '허몽:' or '영몽:' or '정몽:' or '심몽:' depending on the nature of the dream.\n" +
                        "Then structure your response in this exact format with emojis:\n" +
                        "\n" +
                        "🔍 꿈의 주체: [꿈에서 나온 주요 주체나 대상을 직접 명시, 예: 불, 지각, 시험 등]\n" +
                        "[꿈에서 나온 주체에 대한 설명 - 무엇이 중요했는지, 어떤 상징성을 가지는지]\n" +
                        "\n" +
                        "😊/😨 꿈의 감정: [주요 감정을 직접 명시, 예: 불안과 스트레스, 기쁨과 설렘 등]\n" +
                        "[꿈에서 느껴진 감정들과 그 의미에 대한 설명]\n" +
                        "\n" +
                        "💭 꿈의 의미: [왜 이런 꿈을 꾸었는지]\n" +
                        "\n" +
                        "[꿈이 나타내는 심리상태나 현실에서의 연관성에 대한 해석]\n" +
                        "\n" +
                        "Make sure to format the titles exactly as shown, with emojis at the beginning and including the specific subject or emotions in the titles. IMPORTANT: In the '꿈의 의미' section, add a blank line after the title before starting the explanation. Your response must be written in Korean and should be between 450 and 500 characters.",
                topic
        );
    }


    private String createImagePrompt(String topic) {
        return String.format(
                "\"%s\"에 관한 꿈을 고해상도로 시각화한 이미지. " +
                        "꿈의 핵심 감정(공포, 혼란, 기쁨, 신비로움 등)을 시각적으로 자연스럽게 반영하고, " +
                        "비현실적이고 초현실적인 분위기를 가진 장면으로 구성. " +
                        "빛과 그림자, 색채, 인물의 동세와 표정 등을 통해 감정과 분위기를 전달할 것. " +
                        "텍스트나 문구, 로고 등은 절대 포함하지 말 것.",
                topic
        );
    }


    public MultipartFile convertImageUrlToMultipartFile(String imageUrl) throws Exception {
        // 1. 이미지 URL로부터 InputStream 열기
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();

        // 2. InputStream → byte[]
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] imageBytes = buffer.toByteArray();

        // 3. byte[]로 MultipartFile 생성
        return new MockMultipartFile(
                "file",                     // name
                "image.jpg",                // original filename
                "image/jpeg",               // content type
                imageBytes                  // byte[]
        );
    }
}