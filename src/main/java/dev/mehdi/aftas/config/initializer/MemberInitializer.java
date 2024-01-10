package dev.mehdi.aftas.config.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import dev.mehdi.aftas.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//@Order(3)
@AllArgsConstructor
public class MemberInitializer implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("seeders/members.json");
        MemberRequestDto[] members = objectMapper.readValue(resource.getInputStream(), MemberRequestDto[].class);
        memberService.saveAllDto(List.of(members));
    }
}
