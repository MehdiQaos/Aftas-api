package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.model.Fish;
import dev.mehdi.aftas.domain.model.Level;
import dev.mehdi.aftas.dto.fish.FishRequestDto;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.FishRepository;
import dev.mehdi.aftas.service.FishService;
import dev.mehdi.aftas.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FishServiceImpl implements FishService {
    private final FishRepository fishRepository;
    private final LevelService levelService;
    public List<Fish> findAll() {
        return fishRepository.findAll();
    }

    public Optional<Fish> findById(Long id) {
        return fishRepository.findById(id);
    }

    public Optional<Fish> findByName(String name) {
        return fishRepository.findByName(name);
    }

    @Override
    public Fish save(Fish fish) {
        String fishName = fish.getName();

        if (findByName(fish.getName()).isPresent())
            throw new ResourceNotFoundException("Fish by same name already exists");

        return fishRepository.save(fish);
    }

    @Override
    public Fish save(FishRequestDto fishDto) {
        Fish fish = FishRequestDto.toFish(fishDto);
        Level level = levelService.findById(fishDto.getLevelId()).orElseThrow(
                () -> new ResourceNotFoundException("Level not found")
        );
        fish.setLevel(level);
        return save(fish);
    }

    @Override
    public List<Fish> saveAll(List<FishRequestDto> fishesDto) {
        //TODO: check if fishs already exists
        List<Fish> fishes = fishesDto.stream()
                .map(dto -> {
                    Fish fish = FishRequestDto.toFish(dto);
                    Level level = levelService.findById(dto.getLevelId()).orElseThrow(
                            () -> new ResourceNotFoundException("Level not found")
                    );
                    fish.setLevel(level);
                    return fish;
                })
                .toList();
        return fishRepository.saveAll(fishes);
    }
}
