package dev.mehdi.aftas.dto.hunting;

import dev.mehdi.aftas.domain.model.Hunting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class HuntingResponseDto {
    private Long id;
    private Integer numberOfFishes;
    private Long fishId;
    private Long rankingId;

    public static HuntingResponseDto fromModel(Hunting hunting) {
        return new HuntingResponseDto(
            hunting.getId(),
            hunting.getNumberOfFishes(),
            hunting.getFish().getId(),
            hunting.getRanking().getId()
        );
    }
}
