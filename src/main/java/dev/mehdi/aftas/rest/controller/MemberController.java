package dev.mehdi.aftas.rest.controller;

import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import dev.mehdi.aftas.dto.member.MemberResponseDto;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    ResponseEntity<Page<MemberResponseDto>> AllWithPaginationAndSorting(Pageable pageable) {
        Page<Member> membersPage =
                memberService.findAllWithPaginationAndSorting(pageable);
        Page<MemberResponseDto> membersResponseDtoPage =
                membersPage.map(MemberResponseDto::fromModel);

        return ResponseEntity.ok().body(membersResponseDtoPage);
    }

    @GetMapping("email/{email}")
    ResponseEntity<MemberResponseDto> byEmail(@PathVariable String email) {
        Member member = memberService.getByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        MemberResponseDto memberResponseDto = MemberResponseDto.fromModel(member);
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @GetMapping("{id}")
    ResponseEntity<MemberResponseDto> one(@PathVariable Long id) {
        Member member = memberService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        MemberResponseDto memberResponseDto = MemberResponseDto.fromModel(member);
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<MemberResponseDto> create(@RequestBody @Valid MemberRequestDto memberDto) {
        Member createdMember = memberService.save(memberDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.fromModel(createdMember);
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @GetMapping("search/{searchTerm}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JURY')")
    ResponseEntity<List<MemberResponseDto>> search(@PathVariable String searchTerm) {
        List<Member> members = memberService.search(searchTerm);
        List<MemberResponseDto> membersResponseDto =
                members.stream().map(MemberResponseDto::fromModel).toList();

        return ResponseEntity.ok().body(membersResponseDto);
    }

    @PostMapping("{id}/{enabled}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Boolean> enable(@PathVariable Long id, @PathVariable Boolean enabled, Authentication authentication) {
        Member member = memberService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Member not found")
        );
        if (((Member) authentication.getPrincipal()).getId().equals(id)) {
            throw new ResourceNotFoundException("You can't disable yourself");
        }
        member.setEnabled(enabled);
        memberService.update(member);
        return ResponseEntity.ok().body(member.getEnabled());
    }
}
