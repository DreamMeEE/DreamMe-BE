package stdev.domain.dreamdiary.presentation.dto.response;

public record SleepRateResponse(
        String day,

        String rate
) {
    public static SleepRateResponse of(String day, String rate) {
        return new SleepRateResponse(day, rate);
    }
}
