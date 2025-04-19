package stdev.domain.dreamdiary.application.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stdev.domain.dreamdiary.application.CalendarService;
import stdev.domain.dreamdiary.domain.entity.DreamDiary;
import stdev.domain.dreamdiary.domain.repository.DreamDiaryRepository;
import stdev.domain.dreamdiary.presentation.dto.request.CalendarRequest;
import stdev.domain.dreamdiary.presentation.dto.response.CalendarResponse;
import stdev.domain.record.domain.entity.Record;
import stdev.domain.record.domain.repository.RecordRepository;
import stdev.domain.user.domain.entity.User;
import stdev.domain.user.domain.repository.UserRepository;
import stdev.domain.user.infra.exception.UserNotFoundException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {
    private final RecordRepository recordRepository;

    private final DreamDiaryRepository dreamDiaryRepository;
    private final UserRepository userRepository;

    @Override
    public List<CalendarResponse> getMonth(String userId, CalendarRequest req) {

        List<DreamDiary> diaries = dreamDiaryRepository.findDreamDiariesByUserIdAndMonth(userId, req.year(), req.month());

        Map<Integer, DreamDiary> map = new HashMap<>();
        for (DreamDiary d : diaries) {
            int day = d.getRecord().getDreamDiary().getSleepStart().getDayOfMonth();

            map.put(day, d);
        }

        int daysInMonth = YearMonth.of(req.year(), req.month()).lengthOfMonth();
        List<CalendarResponse> result = new ArrayList<>();

        for (int day = 1; day <= daysInMonth; day++) {
            if (map.containsKey(day)) {
                DreamDiary d = map.get(day);
                Duration duration = Duration.between(d.getSleepStart(), d.getSleepEnd());

                result.add(new CalendarResponse(day, d.getRate(), duration.toHours(), d.getRecord().getId()));
            } else {
                result.add(new CalendarResponse(day, "0", 0L, 0L));
            }
        }

        return result;
    }

    @Override
    public List<CalendarResponse> getWeek(String userId, CalendarRequest req) {
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

        List<CalendarResponse> responses = new ArrayList<>();
        for (LocalDate date : weekDates) {
            DreamDiary d = diaryMap.get(date.getDayOfMonth());
            if (d != null) {


                responses.add(CalendarResponse.of(
                        date.getDayOfMonth(),
                        d.getRate(),
                        Duration.between(d.getSleepStart(), d.getSleepEnd()).toHours(),
                        d.getId()
                ));
            } else {
                responses.add(CalendarResponse.of(date.getDayOfMonth(), null, null, null));
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


