package stdev.domain.dreamdiary.presentation.dto.request;

public record CalendarRequest(
        int year,

        int month,

        int day
) {
    public static CalendarRequest of(int year, int month, int day){
        return new CalendarRequest(year,month,day);
    }
}
