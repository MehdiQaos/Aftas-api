package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.model.Fish;
import dev.mehdi.aftas.domain.model.Level;
import dev.mehdi.aftas.dto.fish.FishRequestDto;
import dev.mehdi.aftas.exception.ResourceExistException;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.FishRepository;
import dev.mehdi.aftas.service.FishService;
import dev.mehdi.aftas.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Fish> findAllWithPaginationAndSorting(Pageable pageable) {
        return fishRepository.findAll(pageable);
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

        if (findByName(fishName).isPresent())
            throw new ResourceExistException("Fish by same name already exists");

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

    @Override
    public Fish deleteById(Long id) {
        Fish fish = findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fish not found with id: " + id)
        );
        fishRepository.delete(fish);
        return fish;
    }

    @Override
    public Fish update(Long id, FishRequestDto fishDto) {
        Fish existingFish = findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fish not found with id: " + id)
        );

        Optional<Fish> fishWithNewName = findByName(fishDto.getName());
        if (fishWithNewName.isPresent() && !fishWithNewName.get().getId().equals(id)) {
            throw new ResourceExistException("Fish with name " + fishDto.getName() + " already exists");
        }

        Fish updatedFish = FishRequestDto.toFish(fishDto);
        Level level = levelService.findById(fishDto.getLevelId()).orElseThrow(
                () -> new ResourceNotFoundException("Level not found")
        );
        updatedFish.setLevel(level);
        updatedFish.setId(existingFish.getId()); // preserve the id

        return fishRepository.save(updatedFish);
    }
}
