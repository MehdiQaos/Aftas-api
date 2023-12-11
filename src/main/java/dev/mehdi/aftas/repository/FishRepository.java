package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {
    Optional<Fish> findByName(String name);
}
