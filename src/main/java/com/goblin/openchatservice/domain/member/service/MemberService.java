package com.goblin.openchatservice.domain.member.service;

import com.goblin.openchatservice.domain.auth.TokenProvider;
import com.goblin.openchatservice.domain.member.model.CreateMember;
import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider jwtProvider;

    public String create(CreateMember createMember) {
        String encode = passwordEncoder.encode(createMember.password());
        Member member = Member.from(createMember, encode);
        Member save = memberRepository.save(member);
        return jwtProvider.createToken(save.id());
    }

}
