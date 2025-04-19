package stdev.domain.dreamdiary.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stdev.domain.dreamdiary.application.DreamDiaryService;
import stdev.domain.dreamdiary.presentation.dto.request.CalendarRequest;
import stdev.domain.dreamdiary.presentation.dto.request.DiaryPostRequest;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryGetResponse;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryPostResponse;
import stdev.domain.dreamdiary.presentation.dto.response.SleepRateResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class DreamDiaryController {

    private final DreamDiaryService dreamDiaryService;

    @PostMapping("/dream")
    public ResponseEntity<DiaryPostResponse> dreamDiaryPost(@RequestBody DiaryPostRequest req,
                                                            @AuthenticationPrincipal String userId) {

        DiaryPostResponse diaryPostResponse = dreamDiaryService.dreamPost(req, userId);
        return ResponseEntity.ok(diaryPostResponse);
    }

    @GetMapping("/dream/diary/{id}")
    public ResponseEntity<DiaryGetResponse> dreamDiaryGet(@PathVariable Long id) {
        return ResponseEntity.ok(dreamDiaryService.dreamGet(id));
    }


    @GetMapping("/sleep/rate")
    public ResponseEntity<List<SleepRateResponse>> sleepRate(@ModelAttribute CalendarRequest req,
            @AuthenticationPrincipal String userId) {

        return ResponseEntity.ok(dreamDiaryService.sleepRate(userId, req));
    }
}
