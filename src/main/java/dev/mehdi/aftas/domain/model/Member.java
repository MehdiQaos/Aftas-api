package dev.mehdi.aftas.domain.model;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String nationality;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private IdentityDocumentType identityDocument;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToMany
    Set<Competition> competitions;
}
