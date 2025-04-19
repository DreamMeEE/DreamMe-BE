package stdev.domain.dreamdiary.application;

import stdev.domain.dreamdiary.presentation.dto.request.CalendarRequest;
import stdev.domain.dreamdiary.presentation.dto.response.CalendarResponse;

import java.util.List;

public interface CalendarService {

    List<CalendarResponse> getMonth(String userId, CalendarRequest req);

    List<CalendarResponse> getWeek(String userId, CalendarRequest req);
}
