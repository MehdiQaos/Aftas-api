package dev.mehdi.aftas.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer level;

    private String description;

    private Integer points;

    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
    private final Set<Fish> fishes = new HashSet<>();
}
