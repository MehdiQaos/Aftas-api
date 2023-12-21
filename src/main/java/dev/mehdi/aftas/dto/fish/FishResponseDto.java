package dev.mehdi.aftas.dto.fish;

import dev.mehdi.aftas.domain.model.Fish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class FishResponseDto {
    private Long id;
    private String name;
    private Float averageWeight;
    private Long levelId;

    public static FishResponseDto fromModel(Fish fish) {
        return new FishResponseDto(
            fish.getId(),
            fish.getName(),
            fish.getAverageWeight(),
            fish.getLevel().getId()
        );
    }
}
