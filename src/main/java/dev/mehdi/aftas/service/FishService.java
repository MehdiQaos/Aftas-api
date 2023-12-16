package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.model.Fish;
import dev.mehdi.aftas.dto.fish.FishRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FishService {
    List<Fish> findAll();

    Page<Fish> findAllWithPaginationAndSorting(Pageable pageable);

    Optional<Fish> findById(Long id);
    Optional<Fish> findByName(String name);
    Fish save(Fish fish);
    Fish save(FishRequestDto fishDto);
    List<Fish> saveAll(List<FishRequestDto> fishesDto);
    Fish deleteById(Long id);

    Fish update(Long id, FishRequestDto fishDto);
}
