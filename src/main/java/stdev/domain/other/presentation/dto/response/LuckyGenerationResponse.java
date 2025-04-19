package stdev.domain.other.presentation.dto.response;

public record LuckyGenerationResponse(
        String text,

        String image

) {

    public static LuckyGenerationResponse of(String text, String image) {
        return new LuckyGenerationResponse(text,image);
    }
}
