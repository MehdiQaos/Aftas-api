package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.model.Competition;
import dev.mehdi.aftas.domain.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    boolean existsByDate(LocalDate date);
    Optional<Competition> findByDate(LocalDate date);
    Optional<Competition> findByDateAndIdNot(LocalDate date, Long id);
}
