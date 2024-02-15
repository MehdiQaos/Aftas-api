package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.enums.MemberRole;
import dev.mehdi.aftas.domain.model.Role;

import java.util.Optional;

public interface RoleService {
    public Optional<Role> getById(Long id);

    Optional<Role> getByName(String name);

    Optional<Role> getByName(MemberRole name);

    Role create(Role role);

    Optional<Role> createIfNotExist(Role role);
}
