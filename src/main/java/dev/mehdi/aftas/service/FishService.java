package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.model.Fish;
import dev.mehdi.aftas.dto.fish.FishRequestDto;

import java.util.List;
import java.util.Optional;

public interface FishService {
    List<Fish> findAll();
    Optional<Fish> findById(Long id);
    Optional<Fish> findByName(String name);
    Optional<Fish> save(Fish fish);
    List<Fish> saveAll(List<FishRequestDto> fishsDto);
}
