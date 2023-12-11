package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByLevel(Integer level);
    Optional<Level> findByPoints(Integer points);
//    List<Level> saveAll(Level[] levels);
}
