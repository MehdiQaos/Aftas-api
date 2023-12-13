package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    boolean existsByDate(LocalDate date);
}
