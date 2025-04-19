package stdev.domain.dreamdiary.presentation.dto.response;

public record CalendarResponse(
        int day,

        String rate,

        Long time,
        Long id
) {
    public static CalendarResponse of(int day,

                                      String rate,

                                      Long time, Long id) {
        return new CalendarResponse(day, rate, time,id);
    }
}
