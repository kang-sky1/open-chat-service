package com.goblin.openchatservice.domain.member.service;

import com.goblin.openchatservice.domain.auth.TokenProvider;
import com.goblin.openchatservice.domain.member.model.CreateMember;
import com.goblin.openchatservice.domain.member.model.LoginMember;
import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.member.service.port.MemberRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider jwtProvider;

    public Member create(CreateMember createMember) {
        String encode = passwordEncoder.encode(createMember.password());
        Member member = Member.from(createMember, encode);
        return memberRepository.save(member);
    }

    public String login(LoginMember loginMember) {
        Member member = memberRepository.findByEmail(loginMember.email());

        if(!passwordEncoder.matches(loginMember.password(), member.password())){
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(member.id());
    }

}
