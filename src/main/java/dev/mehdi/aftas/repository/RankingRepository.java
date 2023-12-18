package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findAllByCompetitionId(Long CompetitionId);
}
