package stdev.domain.dreamanalysis.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdev.domain.dreamanalysis.domain.entity.DreamAnalysis;
@Repository
public interface DreamAnalysisRepository extends JpaRepository<DreamAnalysis, Long> {
}
