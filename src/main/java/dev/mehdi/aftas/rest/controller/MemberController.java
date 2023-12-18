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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

//    @GetMapping
//    ResponseEntity<List<MemberResponseDto>> All(
//            @RequestParam(required = false) String sortField,
//            @RequestParam(required = false, defaultValue = "ASC") String sortDirection
//    ) {
//        System.out.println(sortField + " " + sortDirection);
//        List<Member> members;
//        if (sortField != null) {
//            Sort.Direction sd = "ASC".equals(sortDirection) ? Sort.Direction.ASC :
//                    Sort.Direction.DESC;
//            members = memberService.findAllWithSort(sortField, sd);
//        } else {
//            members = memberService.findAll();
//        }
//        List<MemberResponseDto> membersResponseDto = members
//                .stream()
//                .map(MemberResponseDto::fromMember)
//                .toList();
//
//        return ResponseEntity.ok().body(membersResponseDto);
//    }

    @GetMapping
    ResponseEntity<Page<MemberResponseDto>> AllWithPaginationAndSorting(Pageable pageable) {
        Page<Member> membersPage =
                memberService.findAllWithPaginationAndSorting(pageable);
        Page<MemberResponseDto> membersResponseDtoPage =
                membersPage.map(MemberResponseDto::fromModel);

        return ResponseEntity.ok().body(membersResponseDtoPage);
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
    ResponseEntity<MemberResponseDto> create(@RequestBody @Valid MemberRequestDto memberDto) {
        Member createdMember = memberService.save(memberDto);
        MemberResponseDto memberResponseDto = MemberResponseDto.fromModel(createdMember);
        return ResponseEntity.ok().body(memberResponseDto);
    }

    @GetMapping("search/{searchTerm}")
    ResponseEntity<List<MemberResponseDto>> search(@PathVariable String searchTerm) {
        List<Member> members = memberService.search(searchTerm);
        List<MemberResponseDto> membersResponseDto =
                members.stream().map(MemberResponseDto::fromModel).toList();

        return ResponseEntity.ok().body(membersResponseDto);
    }
}
