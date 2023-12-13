package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.model.Fish;
import dev.mehdi.aftas.domain.model.Hunting;
import dev.mehdi.aftas.domain.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HuntingRepository extends JpaRepository<Hunting, Long> {
    Optional<Hunting> findByFishAndRanking(Fish fish, Ranking ranking);
}
