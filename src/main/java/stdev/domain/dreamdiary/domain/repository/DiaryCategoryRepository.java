package stdev.domain.dreamdiary.domain.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stdev.domain.dreamdiary.domain.entity.DiaryCategory;
import stdev.domain.dreamdiary.domain.entity.DreamDiary;

import java.util.List;

@Repository
public interface DiaryCategoryRepository extends JpaRepository<DiaryCategory, Long> {
}
