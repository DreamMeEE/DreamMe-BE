package stdev.domain.dreamanalysis.presentation.dto.response;

public record DreamImageResponse(
        String imageUrl
) {
    public static DreamImageResponse of(String imageUrl) {
        return new DreamImageResponse(imageUrl);
    }
}
