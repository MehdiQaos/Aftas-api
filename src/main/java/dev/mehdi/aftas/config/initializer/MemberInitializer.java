package dev.mehdi.aftas.config.initializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import dev.mehdi.aftas.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(4)
@AllArgsConstructor
public class MemberInitializer implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("seeders/members.json");
        MemberRequestDto[] members = objectMapper.readValue(resource.getInputStream(), MemberRequestDto[].class);
        memberService.saveAllDto(List.of(members));

        memberService.newAdmin(
                MemberRequestDto.builder()
                        .email("admin@admin.com")
                        .firstName("Admin")
                        .lastName("Admin")
                        .password("admin")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .nationality("Morocco")
                        .identityNumber("AD123456")
                        .identityDocumentTypeId(1L)
                        .build()
        );

        memberService.newJury(
                MemberRequestDto.builder()
                        .email("jury@jury.com")
                        .firstName("jury")
                        .lastName("jury")
                        .password("jury")
                        .birthDate(LocalDate.of(1994, 1, 1))
                        .nationality("Morocco")
                        .identityNumber("AD123457")
                        .identityDocumentTypeId(1L)
                        .build()
        );

        memberService.newUser(
                MemberRequestDto.builder()
                        .email("user@user.com")
                        .firstName("user")
                        .lastName("user")
                        .password("user")
                        .birthDate(LocalDate.of(1998, 1, 1))
                        .nationality("Morocco")
                        .identityNumber("AD123458")
                        .identityDocumentTypeId(1L)
                        .build()
        );
    }
}
