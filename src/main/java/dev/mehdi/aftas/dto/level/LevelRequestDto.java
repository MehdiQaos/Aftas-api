package dev.mehdi.aftas.dto.level;

import dev.mehdi.aftas.domain.model.Level;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class LevelRequestDto {
    @NotBlank(message = "Description is required")
    private String description;

    private Integer level;

    @NotBlank(message = "Points is required")
    private Integer points;

    public static Level toLevel(LevelRequestDto levelRequestDto) {
        return Level.builder()
                .description(levelRequestDto.getDescription())
                .level(levelRequestDto.getLevel())
                .points(levelRequestDto.getPoints())
                .build();
    }
}
