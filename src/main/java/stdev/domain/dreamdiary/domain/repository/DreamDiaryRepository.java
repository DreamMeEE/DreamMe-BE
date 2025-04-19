package stdev.domain.dreamdiary.domain.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stdev.domain.dreamdiary.domain.entity.DreamDiary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DreamDiaryRepository extends JpaRepository<DreamDiary, Long> {
    @Query(value = """
            SELECT d.*
            FROM dream_diary d
            JOIN record r ON r.dream_diary_id = d.id
            WHERE r.user_id = :userId
              AND DATE(d.sleep_start) BETWEEN CURRENT_DATE - INTERVAL 6 DAY AND CURRENT_DATE
            ORDER BY d.sleep_start ASC
            """, nativeQuery = true)
    List<DreamDiary> findLast7DaysDreamsByUserId(@Param("userId") String userId);


    @Query("""
            SELECT d 
            FROM DreamDiary d 
            JOIN d.record r 
            JOIN r.user u 
            WHERE u.id = :userId 
            AND FUNCTION('MONTH', d.sleepStart) = :month 
            AND FUNCTION('YEAR', d.sleepStart) = :year
            """)
    List<DreamDiary> findDreamDiariesByUserIdAndMonth(
            @Param("userId") String userId,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("""
    SELECT d FROM DreamDiary d
    WHERE FUNCTION('YEAR', d.sleepStart) = :year
      AND FUNCTION('MONTH', d.sleepStart) = :month
      AND FUNCTION('DAY', d.sleepStart) = :day
""")
    List<DreamDiary> findBySleepStartYearAndMonthAndDay(@Param("year") int year,
                                                        @Param("month") int month,
                                                        @Param("day") int day);


    @Query("""
    SELECT d FROM DreamDiary d
    JOIN d.record r
    JOIN r.user u
    WHERE u.id = :userId
    AND DATE(d.sleepStart) BETWEEN :startDate AND :endDate
""")
    List<DreamDiary> findWeekDreamsByUserId(
            @Param("userId") String userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


}