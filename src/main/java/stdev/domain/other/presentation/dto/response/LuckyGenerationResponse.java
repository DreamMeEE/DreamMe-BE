package stdev.domain.other.presentation.dto.response;

public record LuckyGenerationResponse(
        String text,

        String image,

        String title

) {

    public static LuckyGenerationResponse of(String text, String image, String title) {
        return new LuckyGenerationResponse(text,image,title);
    }
}
