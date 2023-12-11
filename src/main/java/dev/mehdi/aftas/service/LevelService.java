package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.model.Level;
import dev.mehdi.aftas.dto.level.LevelRequestDto;

import java.util.List;
import java.util.Optional;

public interface LevelService {
    List<Level> findAll();
    Optional<Level> findById(Long id);
    Optional<Level> findByLevel(Integer level);
    Optional<Level> findByPoints(Integer points);
    Level save(Level level);
    List<Level> saveAll(List<LevelRequestDto> levels);
}
