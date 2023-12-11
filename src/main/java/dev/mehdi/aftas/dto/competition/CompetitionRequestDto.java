package dev.mehdi.aftas.dto.competition;

import dev.mehdi.aftas.dto.hunting.HuntingRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class CompetitionRequestDto {
    private String code;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private Map<Integer, List<HuntingRequestDto>> memberHuntings;
}
