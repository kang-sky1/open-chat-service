package com.goblin.openchatservice.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.goblin.openchatservice.domain.auth.TokenProvider;
import com.goblin.openchatservice.domain.member.mock.FakeMemberDto;
import com.goblin.openchatservice.domain.member.mock.FakeMemberRepository;
import com.goblin.openchatservice.domain.member.mock.FakePasswordEncoder;
import com.goblin.openchatservice.domain.member.mock.FakeTokenProvider;
import com.goblin.openchatservice.domain.member.model.CreateMember;
import com.goblin.openchatservice.domain.member.service.port.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;
    private TokenProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public MemberServiceTest() {
        memberRepository = new FakeMemberRepository();
        jwtProvider = new FakeTokenProvider();
        passwordEncoder = new FakePasswordEncoder();
        memberService = new MemberService(memberRepository, passwordEncoder, jwtProvider);
    }

    @Test
    @DisplayName("유저를 생성하면 토큰을 반환한다.")
    void create() {
        CreateMember createMember = FakeMemberDto.createMember();

        String token = memberService.create(createMember);

        assertThat(token)
            .isNotNull()
            .isNotEmpty();
    }


}