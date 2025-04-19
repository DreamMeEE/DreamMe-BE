package stdev.domain.dreamdiary.application;

import org.springframework.stereotype.Repository;
import stdev.domain.dreamdiary.presentation.dto.request.CalendarRequest;
import stdev.domain.dreamdiary.presentation.dto.request.DiaryPostRequest;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryGetResponse;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryPostResponse;
import stdev.domain.dreamdiary.presentation.dto.response.SleepRateResponse;

import java.util.List;

public interface DreamDiaryService {
    DiaryPostResponse dreamPost(DiaryPostRequest req, String userId);

    DiaryGetResponse dreamGet(Long id);

    List<SleepRateResponse> sleepRate(String userId, CalendarRequest req);
}
