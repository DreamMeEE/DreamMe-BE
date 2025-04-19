package stdev.domain.record.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdev.domain.dreamdiary.domain.entity.DreamDiary;
import stdev.domain.record.domain.entity.Record;
import stdev.domain.user.domain.entity.User;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUser(User user);
}
