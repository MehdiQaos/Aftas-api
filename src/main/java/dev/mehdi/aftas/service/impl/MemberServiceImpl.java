package dev.mehdi.aftas.service.impl;

import dev.mehdi.aftas.domain.enums.IdentityDocumentType;
import dev.mehdi.aftas.domain.model.Member;
import dev.mehdi.aftas.dto.member.MemberRequestDto;
import dev.mehdi.aftas.exception.ResourceExistException;
import dev.mehdi.aftas.exception.ResourceNotFoundException;
import dev.mehdi.aftas.repository.MemberRepository;
import dev.mehdi.aftas.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Page<Member> findAllWithPaginationAndSorting(Pageable pageable) {
        return memberRepository.findAll(pageable);
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
    public Member save(MemberRequestDto memberRequestDto) {
        validate(memberRequestDto);
        Member member = memberRequestDto.toMember();
        return save(member);
    }

    @Override
    public List<Member> saveAll(List<Member> members) {
        members.forEach(this::validate);
        return memberRepository.saveAll(members);
    }

    @Override
    public List<Member> saveAllDto(List<MemberRequestDto> members) {
        List<Member> memberList = members.stream()
                .filter(this::validate)
                .map(MemberRequestDto::toMember)
                .toList();

        return saveAll(memberList);
    }
}
