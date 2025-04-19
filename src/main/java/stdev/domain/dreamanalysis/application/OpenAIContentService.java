package stdev.domain.dreamanalysis.application;

import stdev.domain.dreamanalysis.presentation.dto.response.*;

import java.util.concurrent.CompletableFuture;

public interface OpenAIContentService {
    CompletableFuture<ContentGenerationResponse> generateContent(String topic);


    DreamCommentResponse generateComment(String topic);

    DreamImageResponse generateImage(String topic);

    HeadResponse generateHead(String category);

    ContentResponse getContent(Long id, String userId);

}