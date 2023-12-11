package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.domain.model.Fish;
import dev.mehdi.aftas.dto.fish.FishResponseDto;
import dev.mehdi.aftas.service.FishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fish")
@RequiredArgsConstructor
public class FishController {
    private final FishService fishService;

    @GetMapping
    ResponseEntity<List<FishResponseDto>> All() {
        List<FishResponseDto> fishResponseDtos = fishService.findAll()
            .stream()
            .map(FishResponseDto::fromModel)
            .toList();

        return ResponseEntity.ok().body(fishResponseDtos);
    }

    @GetMapping("/{id}")
    ResponseEntity<FishResponseDto> one(@RequestParam Long id) {
        Fish fish = fishService.findById(id).orElseThrow();
        FishResponseDto fishResponseDto = FishResponseDto.fromModel(fish);
        return ResponseEntity.ok().body(fishResponseDto);
    }
}
