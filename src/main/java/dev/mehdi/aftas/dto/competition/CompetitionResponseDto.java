package dev.mehdi.aftas.dto.competition;

import dev.mehdi.aftas.domain.model.Competition;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class CompetitionResponseDto {
    private Long id;

    private String code;

    private LocalDate date;

    private Integer numberOfParticipants;

    private LocalTime startTime;

    private LocalTime endTime;

    private String location;

    private Float amount;

    public static CompetitionResponseDto from(Competition competition) {
        return new CompetitionResponseDto(
                competition.getId(),
                competition.getCode(),
                competition.getDate(),
                competition.getNumberOfParticipants(),
                competition.getStartTime(),
                competition.getEndTime(),
                competition.getLocation(),
                competition.getAmount()
        );
    }
}
