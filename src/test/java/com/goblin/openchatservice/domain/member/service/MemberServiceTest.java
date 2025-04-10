package com.goblin.openchatservice.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.goblin.openchatservice.domain.auth.TokenProvider;
import com.goblin.openchatservice.domain.member.mock.FakeMemberDto;
import com.goblin.openchatservice.domain.member.mock.FakeMemberRepository;
import com.goblin.openchatservice.domain.member.mock.FakeTokenProvider;
import com.goblin.openchatservice.domain.member.model.CreateMember;
import com.goblin.openchatservice.domain.member.model.LoginMember;
import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.member.service.port.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;
    private TokenProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    public MemberServiceTest() {
        memberRepository = new FakeMemberRepository();
        jwtProvider = new FakeTokenProvider();
        passwordEncoder = new BCryptPasswordEncoder();
        memberService = new MemberService(memberRepository, passwordEncoder, jwtProvider);
    }

    @DisplayName("멤버를 생성한다.")
    @Test
    void create() {
        CreateMember createMember = FakeMemberDto.createMember();

        Member result = memberService.create(createMember);
        Member findMember = memberRepository.findById(result.id());
        assertThat(result)
                .extracting("id", "name", "email", "password")
                .contains(
                        findMember.id(), findMember.name(), findMember.email(), findMember.password()
                );
    }


    @DisplayName("로그인 성공하면, 토큰을 반환한다.")
    @Test
    void login() {
        CreateMember createMember = FakeMemberDto.createMember();
        memberService.create(createMember);

        LoginMember loginMember = new LoginMember("testEmail@naver.com", "testPassword");
        String token = memberService.login(loginMember);

        assertThat(token)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 아이디면, 에러가 발생한다.")
    void login_invalidId() {
        CreateMember createMember = FakeMemberDto.createMember();
        memberService.create(createMember);

        LoginMember loginMember = new LoginMember("testEmail2@naver.com", "testPassword");
        assertThatThrownBy(() -> memberService.login(loginMember)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("패스워드가 일치하지 않으면 에러가 발생한다.")
    void login_invalidPassword() {
        CreateMember createMember = FakeMemberDto.createMember();
        memberService.create(createMember);

        LoginMember loginMember = new LoginMember("testEmail@naver.com", "testPasswor");

        assertThatThrownBy(() -> memberService.login(loginMember)).isInstanceOf(IllegalArgumentException.class);
    }

}