package dev.mehdi.aftas.dto.competition;

import dev.mehdi.aftas.domain.model.Competition;
import dev.mehdi.aftas.dto.hunting.HuntingInitializationDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class CompetitionHuntingsDto {
    private String code;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private Map<Integer, List<HuntingInitializationDto>> memberHuntings;

    public Competition toCompetition() {
        return Competition.builder()
                .code(code)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .location(location)
                .build();
    }
}
