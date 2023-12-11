package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.dto.member.MemberResponseDto;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    ResponseEntity<List<MemberResponseDto>> All() {
        List<MemberResponseDto> membersResponseDto = memberService.findAll()
            .stream()
            .map(MemberResponseDto::fromMember)
            .toList();

        return ResponseEntity.ok().body(membersResponseDto);
    }

    @GetMapping("{id}")
    ResponseEntity<MemberResponseDto> one(@PathVariable Long id) {
        Member member = memberService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        MemberResponseDto memberResponseDto = MemberResponseDto.fromMember(member);
        return ResponseEntity.ok().body(memberResponseDto);
    }
}
