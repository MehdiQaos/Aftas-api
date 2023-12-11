package dev.mehdi.aftas.config.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

public class InitializeSeeders implements CommandLineRunner {
    private final ApplicationContext applicationContext;

    private final Class<? extends IInitializer>[] initializersList = new Class[] {
//            RoleDataInitializer.class,
    };

    public InitializeSeeders(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        Arrays.stream(initializersList).forEach(
                (initializerClass) -> applicationContext
                        .getBean(initializerClass).initialize()
        );
    }
}
