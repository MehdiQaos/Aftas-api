package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.model.Level;
import dev.mehdi.aftas.dto.level.LevelRequestDto;
import dev.mehdi.aftas.repository.LevelRepository;
import dev.mehdi.aftas.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;
    @Override
    public List<Level> findAll() {
        return levelRepository.findAll();
    }

    @Override
    public Optional<Level> findById(Long id) {
        return levelRepository.findById(id);
    }

    @Override
    public Optional<Level> findByLevel(Integer level) {
        return levelRepository.findByLevel(level);
    }

    @Override
    public Optional<Level> findByPoints(Integer points) {
        return levelRepository.findByPoints(points);
    }

    @Override
    public Level save(Level level) {
        return levelRepository.save(level); //TODO: check if level already exists
    }

    @Override
    public List<Level> saveAll(List<LevelRequestDto> levelsDtos) {
        // TODO: check if levels already exists
        List<Level> levels = levelsDtos.stream()
                .map(LevelRequestDto::toLevel)
                .toList();
        return levelRepository.saveAll(levels);
    }
}
