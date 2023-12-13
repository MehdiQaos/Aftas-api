package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.model.Hunting;
import dev.mehdi.aftas.dto.hunting.HuntingInitializationDto;
import dev.mehdi.aftas.dto.hunting.HuntingRequestDto;

import java.util.Optional;

public interface HuntingService {
    Optional<Hunting> findById(Long id);
    Hunting save(Hunting hunting);
    Hunting save(HuntingInitializationDto huntingDto);
    Hunting addHunt(HuntingRequestDto huntingDto);
}
