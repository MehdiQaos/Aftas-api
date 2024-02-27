package dev.mehdi.aftas.service;

import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long id);
    List<Member> findAll();
    Optional<Member> getByEmail(String email);
    Page<Member> findAllWithPaginationAndSorting(Pageable pageable);

    Page<Member> findAllMembersByCompetitionId(
            Long competitionId, Pageable pageable);

    Optional<Member> findByIdentityNumberAndIdentityDocument(Member member);
    Optional<Member> findByFirstNameAndLastName(Member member);
    Member save(Member member);

    Member update(Member member);

    Member save(MemberRequestDto memberRequestDto);

    Member newAdmin(MemberRequestDto memberRequestDto);

    Member newJury(MemberRequestDto memberRequestDto);

    Member newUser(MemberRequestDto memberRequestDto);

    List<Member> saveAll(List<Member> members);
    List<Member> saveAllDto(List<MemberRequestDto> members);

    List<Member> search(String searchTerm);
}
