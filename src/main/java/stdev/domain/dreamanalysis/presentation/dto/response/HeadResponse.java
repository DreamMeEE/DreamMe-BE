package stdev.domain.dreamanalysis.presentation.dto.response;

public record HeadResponse(
        String headImageUrl,

        String headContent
) {
    public static HeadResponse of(String headImageUrl, String headContent){
        return new HeadResponse(headImageUrl, headContent);
    }
}
