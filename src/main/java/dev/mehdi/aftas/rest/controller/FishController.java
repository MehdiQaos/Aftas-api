package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.domain.model.Fish;
import dev.mehdi.aftas.dto.fish.FishRequestDto;
import dev.mehdi.aftas.dto.fish.FishResponseDto;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.service.FishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fish")
@RequiredArgsConstructor
public class FishController {
    private final FishService fishService;

    @GetMapping
    ResponseEntity<Page<FishResponseDto>> All(Pageable pageable) {
        Page<Fish> fishesPage = fishService.findAllWithPaginationAndSorting(pageable);
        Page<FishResponseDto> fishResponseDtoPage =
                fishesPage.map(FishResponseDto::fromModel);

        return ResponseEntity.ok().body(fishResponseDtoPage);
    }

    @GetMapping("{id}")
    ResponseEntity<FishResponseDto> one(@PathVariable Long id) {
        Fish fish = fishService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fish not found with id: " + id)
        );
        FishResponseDto fishResponseDto = FishResponseDto.fromModel(fish);
        return ResponseEntity.ok().body(fishResponseDto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JURY')")
    ResponseEntity<FishResponseDto> create(@RequestBody @Valid FishRequestDto fishDto) {
        Fish createdFish = fishService.save(fishDto);
        FishResponseDto fishResponseDto = FishResponseDto.fromModel(createdFish);
        return new ResponseEntity<>(fishResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JURY')")
    ResponseEntity<FishResponseDto> delete(@PathVariable Long id) {
        Fish deletedFish = fishService.deleteById(id);
        FishResponseDto fishResponseDto = FishResponseDto.fromModel(deletedFish);
        return ResponseEntity.ok().body(fishResponseDto);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JURY')")
    ResponseEntity<FishResponseDto> update(@PathVariable Long id,
            @RequestBody @Valid FishRequestDto fishDto) {

        Fish updatedFish = fishService.update(id, fishDto);
        FishResponseDto fishResponseDto = FishResponseDto.fromModel(updatedFish);
        return ResponseEntity.ok().body(fishResponseDto);
    }
}
