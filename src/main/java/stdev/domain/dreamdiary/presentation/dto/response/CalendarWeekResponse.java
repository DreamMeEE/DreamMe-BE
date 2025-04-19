package stdev.domain.dreamdiary.presentation.dto.response;

public record CalendarWeekResponse(
        int day,

        String rate,

        String time,
        Long id
) {
    public static CalendarWeekResponse of(int day,

                                          String rate,

                                          String time, Long id) {
        return new CalendarWeekResponse(day, rate, time,id);
    }
}
