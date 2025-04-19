package stdev.domain.dreamdiary.presentation.dto.request;

public record CalendarRequest(
        int year,

        int month
) {
    public static CalendarRequest of(int year, int month){
        return new CalendarRequest(year,month);
    }
}
