package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.enums.MemberRole;
import dev.mehdi.aftas.domain.model.Role;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.RoleRepository;
import dev.mehdi.aftas.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> getById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> getByName(String name) {
        MemberRole role;
        try {
            role = MemberRole.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        return getByName(role);
    }

    @Override
    public Optional<Role> getByName(MemberRole role) {
        return roleRepository.findByName(role);
    }

    @Override
    public Role create(Role role) {
        getByName(role.getName()).ifPresent(r -> {
            throw new ResourceNotFoundException("Role already exists");
        });
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> createIfNotExist(Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(roleRepository.save(role));
    }
}
