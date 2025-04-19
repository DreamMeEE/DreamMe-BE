package stdev.domain.other.presentation.dto.request;

public record LuckyGenerationRequest(

        String dreamCategory,
        String topic,

        String luckyCategory
) {

}