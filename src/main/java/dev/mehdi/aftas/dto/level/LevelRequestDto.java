package dev.mehdi.aftas.dto.level;

import dev.mehdi.aftas.domain.model.Level;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class LevelRequestDto {
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Level is required")
    @Min(value = 1, message = "Level must be greater than or equal to 1")
    private Integer level;

    @NotNull(message = "Points is required")
    @Min(value = 0, message = "Points must be greater than or equal to 0")
    private Integer points;

    public static Level toLevel(LevelRequestDto levelRequestDto) {
        return Level.builder()
                .description(levelRequestDto.getDescription())
                .level(levelRequestDto.getLevel())
                .points(levelRequestDto.getPoints())
                .build();
    }
}
