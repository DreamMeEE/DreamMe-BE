package stdev.domain.user.application.service.impl;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import stdev.domain.oauth2.application.service.CreateAccessTokenAndRefreshTokenService;
import stdev.domain.record.domain.entity.Record;
import stdev.domain.record.domain.repository.RecordRepository;
import stdev.domain.user.application.service.UserService;

import stdev.domain.user.domain.entity.User;
import stdev.domain.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import stdev.domain.user.infra.exception.UserNotFoundException;
import stdev.domain.user.presentation.dto.req.MypageRequest;
import stdev.domain.user.presentation.dto.res.UserMypageResponse;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;


    @Override
    public UserMypageResponse mypageGet(String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("회원 정보가 없어요");
        }

        List<Record> byUser = recordRepository.findByUser(user);

        LocalTime start = user.getStartTime();
        LocalTime end = user.getEndTime();

        // Duration 계산 정확히 하기
        Duration duration = Duration.between(start, end);
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

        if (byUser.isEmpty()) {
            return UserMypageResponse.of(user.getName(), user.getProfile(), 0, 0, 0,
                    user.getStartTime(), user.getEndTime(), sb.toString());
        } else {
            float sum = 0;
            int dream = 0;
            for (Record record : byUser) {
                if (record.getDreamAnalysis() != null) {
                    dream++;
                }
                sum += Integer.parseInt(record.getDreamDiary().getRate());
            }


            return UserMypageResponse.of(user.getName(), user.getProfile(), byUser.size(), dream, sum / byUser.size(),
                    user.getStartTime(), user.getEndTime(), sb.toString());
        }


    }

    @Override
    public void mypageUpdate(MypageRequest req, String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("회원 정보가 없어요");
        }

        user.updateTime(req.startTime(), req.endTime());
    }
}
