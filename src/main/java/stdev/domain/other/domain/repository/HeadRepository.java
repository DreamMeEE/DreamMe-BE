package stdev.domain.other.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdev.domain.other.domain.entity.Head;

import java.util.Optional;

@Repository
public interface HeadRepository extends JpaRepository<Head, Long> {
    Optional<Head> findByHeadCategory(String headCategory);
}
