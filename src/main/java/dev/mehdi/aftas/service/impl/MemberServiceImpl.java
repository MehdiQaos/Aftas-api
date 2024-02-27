package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.enums.MemberRole;
import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.domain.model.Role;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import dev.mehdi.aftas.exception.ResourceExistException;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.MemberRepository;
import dev.mehdi.aftas.service.MemberService;
import dev.mehdi.aftas.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Page<Member> findAllWithPaginationAndSorting(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public Page<Member> findAllMembersByCompetitionId(
            Long competitionId, Pageable pageable) {
        return memberRepository
                .findAllMembersByCompetitionId(competitionId, pageable);
    }

    @Override
    public Optional<Member> findByIdentityNumberAndIdentityDocument(Member member) {
        String identityNumber = member.getIdentityNumber();
        IdentityDocumentType identityDocumentType = member.getIdentityDocument();

        return memberRepository.findByIdentityNumberAndIdentityDocument(identityNumber
                , identityDocumentType);
    }

    @Override
    public Optional<Member> findByFirstNameAndLastName(Member member) {
        return memberRepository.findByFirstNameAndLastName(member.getFirstName()
                , member.getLastName());
    }

    private boolean validate(Member member) {
        if (findByFirstNameAndLastName(member).isPresent() ||
                findByIdentityNumberAndIdentityDocument(member).isPresent()) {

            throw new ResourceExistException("Member already exists");
        }
        return true;
    }

    private boolean validate(MemberRequestDto memberRequestDto) {
        Long identityDocumentTypeId = memberRequestDto.getIdentityDocumentTypeId();
        int documentTypeLength = IdentityDocumentType.values().length;
        if (identityDocumentTypeId < 0 || identityDocumentTypeId >= documentTypeLength) {
            throw new ResourceNotFoundException("Invalid identity document type");
        }
        return true;
    }

    @Override
    public Member save(Member member) {
        validate(member);
        return memberRepository.save(member);
    }

    @Override
    public Member update(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member save(MemberRequestDto memberRequestDto) {
        validate(memberRequestDto);
        Member member = memberRequestDto.toMember();
        member.setEnabled(false);
        Role userRole = roleService.getByName(MemberRole.USER).orElseThrow(
                () -> new ResourceNotFoundException("user role not found")
        );
        member.setRole(userRole);
        return save(member);
    }

    @Override
    public Member newAdmin(MemberRequestDto memberRequestDto) {
        validate(memberRequestDto);
        Member member = memberRequestDto.toMember();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setEnabled(true);
        Role adminRole = roleService.getByName(MemberRole.ADMIN).orElseThrow(
                () -> new ResourceNotFoundException("admin role not found")
        );
        member.setRole(adminRole);
        return save(member);
    }

    @Override
    public Member newJury(MemberRequestDto memberRequestDto) {
        validate(memberRequestDto);
        Member member = memberRequestDto.toMember();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setEnabled(false);
        Role juryRole = roleService.getByName(MemberRole.JURY).orElseThrow(
                () -> new ResourceNotFoundException("jury role not found")
        );
        member.setRole(juryRole);
        return save(member);
    }

    @Override
    public Member newUser(MemberRequestDto memberRequestDto) {
        validate(memberRequestDto);
        Member member = memberRequestDto.toMember();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setEnabled(false);
        Role userRole = roleService.getByName(MemberRole.USER).orElseThrow(
                () -> new ResourceNotFoundException("user role not found")
        );
        member.setRole(userRole);
        return save(member);
    }

    @Override
    public List<Member> saveAll(List<Member> members) {
        Role userRole = roleService.getByName(MemberRole.USER).orElseThrow(
                () -> new ResourceNotFoundException("user role not found")
        );
        members.forEach(member -> {
            validate(member);
            member.setRole(userRole);
            member.setPassword(passwordEncoder.encode(member.getPassword()));
        });
        return memberRepository.saveAll(members);
    }

    @Override
    public List<Member> saveAllDto(List<MemberRequestDto> members) {
//        members.forEach(this::save);
//        return List.of();
        List<Member> memberList = members.stream()
                .filter(this::validate)
                .map(MemberRequestDto::toMember)
                .toList();

        return saveAll(memberList);
    }

    @Override
    public List<Member> search(String searchTerm) {
        List<Member> members = new ArrayList<>();
        try {
            Long id = Long.parseLong(searchTerm);
            memberRepository.findById(id).ifPresent(members::add);
        } catch (NumberFormatException e) {
            members = memberRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
        }
        return members;
    }
}
