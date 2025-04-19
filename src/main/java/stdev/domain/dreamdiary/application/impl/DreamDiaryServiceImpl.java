package stdev.domain.dreamdiary.application.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stdev.domain.dreamanalysis.domain.entity.DreamAnalysis;
import stdev.domain.dreamanalysis.domain.repository.DreamAnalysisRepository;
import stdev.domain.dreamdiary.application.DreamDiaryService;
import stdev.domain.dreamdiary.domain.entity.DiaryCategory;
import stdev.domain.dreamdiary.domain.entity.DreamDiary;
import stdev.domain.dreamdiary.domain.repository.DiaryCategoryRepository;
import stdev.domain.dreamdiary.domain.repository.DreamDiaryRepository;
import stdev.domain.dreamdiary.infra.exception.InfromationDiaryException;
import stdev.domain.dreamdiary.presentation.dto.request.CalendarRequest;
import stdev.domain.dreamdiary.presentation.dto.request.DiaryPostRequest;
import stdev.domain.dreamdiary.presentation.dto.response.CalendarResponse;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryGetResponse;
import stdev.domain.dreamdiary.presentation.dto.response.DiaryPostResponse;
import stdev.domain.dreamdiary.presentation.dto.response.SleepRateResponse;
import stdev.domain.record.domain.entity.Record;
import stdev.domain.record.domain.repository.RecordRepository;
import stdev.domain.user.domain.entity.User;
import stdev.domain.user.domain.repository.UserRepository;
import stdev.domain.user.infra.exception.UserNotFoundException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DreamDiaryServiceImpl implements DreamDiaryService {
    private final DreamDiaryRepository dreamDiaryRepository;

    private final DreamAnalysisRepository dreamAnalysisRepository;

    private final RecordRepository recordRepository;

    private final UserRepository userRepository;


    private final DiaryCategoryRepository diaryCategoryRepository;


    @Override
    public DiaryPostResponse dreamPost(DiaryPostRequest req, String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("회원 정보 없음요");
        }

        List<DreamDiary> bySleepStartYearAndMonth = dreamDiaryRepository.findBySleepStartYearAndMonthAndDay(
                req.sleepStart().getYear(), req.sleepStart().getMonthValue(), req.sleepStart().getDayOfMonth());

        if (!bySleepStartYearAndMonth.isEmpty()) {
            throw new InfromationDiaryException();
        }


        if (req.flag()) {
            DreamDiary dreamDiary = DreamDiary.
                    builder().sleepStart(req.sleepStart())
                    .sleepEnd(req.sleepEnd())
                    .note(req.note())
                    .rate(req.rate())
                    .title(req.title())
                    .content(req.content())
                    .diaryCategory(req.diaryCategory())
                    .build();
            DreamAnalysis dreamAnalysis = DreamAnalysis.builder()
                    .flag(true)
                    .dreamCategory(req.dreamCategory())
                    .dreamComment(req.comment())
                    .dreamImageUrl(req.imageUrl())
                    .build();
            Record record = Record.builder()
                    .dreamDiary(dreamDiary)
                    .user(user)
                    .dreamAnalysis(dreamAnalysis)
                    .build();

            DreamDiary diarySave = dreamDiaryRepository.save(dreamDiary);
            diarySave.getId();

//            for (String category : req.categories()) {
//                DiaryCategory diaryCategory = DiaryCategory.builder().name(category).dreamDiary(diarySave).build();
//                diaryCategoryRepository.save(diaryCategory);
//            }
            dreamAnalysisRepository.save(dreamAnalysis);
            Record save = recordRepository.save(record);
            return DiaryPostResponse.of(save.getId());

        } else {
            DreamDiary dreamDiary = DreamDiary.
                    builder().sleepStart(req.sleepStart())
                    .sleepEnd(req.sleepEnd())
                    .note(req.note())
                    .rate(req.rate())
                    .title(req.title())
                    .content(req.content())
                    .diaryCategory(req.diaryCategory())
                    .build();

            Record record = Record.builder()
                    .dreamDiary(dreamDiary)
                    .user(user)
                    .dreamAnalysis(null)
                    .build();
            DreamDiary diarySave = dreamDiaryRepository.save(dreamDiary);

//            for (String category : req.categories()) {
//                DiaryCategory diaryCategory = DiaryCategory.builder().name(category).dreamDiary(diarySave).build();
//                diaryCategoryRepository.save(diaryCategory);
//            }
            Record save = recordRepository.save(record);
            return DiaryPostResponse.of(save.getId());
        }
    }

    @Override
    public DiaryGetResponse dreamGet(Long id) {
        Record record = recordRepository.findById(id).orElse(null);
        if (record == null) {
            throw new UserNotFoundException("기록이 없습니다.");
        }
        DreamDiary dreamDiary = record.getDreamDiary();
//        List<DiaryCategory> diaryCategories = dreamDiary.getDiaryCategories();
//
//        List<String> list = new ArrayList<>();
//        for (DiaryCategory diaryCategory : diaryCategories) {
//            list.add(diaryCategory.getName());
//        }

        DreamAnalysis dreamAnalysis = record.getDreamAnalysis();
        boolean flag = true; // 꿈 분석 있는지 없는지
        if (dreamAnalysis == null) {
            flag = false;
        }
        // Duration 계산 정확히 하기
        Duration duration = Duration.between(dreamDiary.getSleepStart(), dreamDiary.getSleepEnd());
        if (duration.isNegative()) {
            duration = duration.plusHours(24);
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        StringBuilder sb = new StringBuilder();
        if (minutes == 0) {
            sb.append(hours).append("시간");
        } else {
            sb.append(hours).append("시간").append(" ").append(minutes).append("분");

        }


        return DiaryGetResponse.of(dreamDiary.getId(), dreamDiary.getSleepStart().toLocalTime(), dreamDiary.getSleepEnd().toLocalTime(), dreamDiary.getNote()
                , dreamDiary.getRate(), dreamDiary.getTitle(), dreamDiary.getContent(), dreamDiary.getDiaryCategory(), sb.toString(), flag);
    }

    @Override
    public List<SleepRateResponse> sleepRate(String userId, CalendarRequest req) {

        LocalDate today = LocalDate.now();

        // 요청한 달이 오늘 달이 아닌 경우 -> 요청 기준의 날짜로 보정
        if (req.year() != today.getYear() || req.month() != today.getMonthValue()) {
            today = LocalDate.of(req.year(), req.month(), 1);
        }

        List<LocalDate> weekDates = getWeekDates(today);
        LocalDate start = weekDates.get(0);
        LocalDate end = weekDates.get(6);

        List<DreamDiary> diaries = dreamDiaryRepository.findWeekDreamsByUserId(userId, start, end);

        Map<Integer, DreamDiary> diaryMap = diaries.stream()
                .collect(Collectors.toMap(
                        d -> d.getSleepStart().getDayOfMonth(),
                        d -> d
                ));

        List<SleepRateResponse> responses = new ArrayList<>();
        for (LocalDate date : weekDates) {
            DreamDiary d = diaryMap.get(date.getDayOfMonth());
            if (d != null) {


                responses.add(SleepRateResponse.of(
                        String.valueOf(date.getDayOfMonth()),
                        d.getRate()
                ));
            } else {
                responses.add(SleepRateResponse.of(String.valueOf(date.getDayOfMonth()), null));
            }
        }

        return responses;
    }

    public List<LocalDate> getWeekDates(LocalDate today) {
        LocalDate sunday = today.minusDays(today.getDayOfWeek().getValue() % 7);
        return IntStream.range(0, 7)
                .mapToObj(sunday::plusDays)
                .toList();
    }
}
