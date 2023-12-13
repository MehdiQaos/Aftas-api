package dev.mehdi.aftas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float averageWeight;

    @ManyToOne
    private Level level;

    @OneToMany(mappedBy = "fish", fetch = FetchType.LAZY)
    private final List<Hunting> huntings = new ArrayList<>();
}
