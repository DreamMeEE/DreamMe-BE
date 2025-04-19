package stdev.domain.other.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdev.domain.other.domain.entity.Head;
import stdev.domain.other.domain.entity.Lucky;

import java.util.Optional;

@Repository
public interface LuckyRepository extends JpaRepository<Lucky, Long> {
    Optional<Lucky> findByKeyword(String keyword);
}
