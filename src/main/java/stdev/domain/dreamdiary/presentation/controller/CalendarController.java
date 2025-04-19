package stdev.domain.dreamdiary.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import stdev.domain.dreamdiary.application.CalendarService;
import stdev.domain.dreamdiary.application.DreamDiaryService;
import stdev.domain.dreamdiary.presentation.dto.request.CalendarRequest;
import stdev.domain.dreamdiary.presentation.dto.request.DiaryPostRequest;
import stdev.domain.dreamdiary.presentation.dto.response.CalendarResponse;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryGetResponse;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryPostResponse;
import stdev.domain.dreamdiary.presentation.dto.response.SleepRateResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/month")
    public ResponseEntity<List<CalendarResponse>> monthGet(@ModelAttribute CalendarRequest req, @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(calendarService.getMonth(userId, req));
    }


    @GetMapping("/week")
    public ResponseEntity<List<CalendarResponse>> weekGet(@ModelAttribute CalendarRequest req, @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(calendarService.getWeek(userId, req));
    }
}
