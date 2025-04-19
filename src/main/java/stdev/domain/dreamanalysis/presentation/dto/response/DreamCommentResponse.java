package stdev.domain.dreamanalysis.presentation.dto.response;

public record DreamCommentResponse(
        String comment,

        String category,

        boolean flag
) {
    public static DreamCommentResponse of(String comment, String category, boolean flag){
        return new DreamCommentResponse(comment,category,flag);
    }
}
