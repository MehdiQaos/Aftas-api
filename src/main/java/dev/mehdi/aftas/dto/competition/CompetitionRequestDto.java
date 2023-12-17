package dev.mehdi.aftas.dto.competition;

import dev.mehdi.aftas.domain.model.Competition;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter @Setter
public class CompetitionRequestDto {
    @FutureOrPresent(message = "Date must be in the future or present day")
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount cannot be less than 0")
    private Float amount;

    public Competition toCompetition() {
        return Competition.builder()
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .location(location)
                .amount(amount)
                .build();
    }
}