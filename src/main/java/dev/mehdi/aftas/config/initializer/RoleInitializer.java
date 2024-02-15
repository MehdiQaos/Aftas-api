package dev.mehdi.aftas.config.initializer;

import dev.mehdi.aftas.domain.enums.MemberRole;
import dev.mehdi.aftas.domain.model.Role;
import dev.mehdi.aftas.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        Role admin = Role.builder().name(MemberRole.ADMIN).build();
        Role user = Role.builder().name(MemberRole.USER).build();

        roleService.createIfNotExist(admin);
        roleService.createIfNotExist(user);
    }
}
