package dev.mehdi.aftas.repository;

import dev.mehdi.aftas.domain.enums.MemberRole;
import dev.mehdi.aftas.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(MemberRole name);
}
