package com.goblin.openchatservice.domain.member.repository;

import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryIml implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberEntity.from(member)).toModel();
    }

    @Override
    public Member findById(Long id) {
        return null;
    }

    @Override
    public Member findByEmail(String email) {
        return memberJpaRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."))
                .toModel();
    }

}
