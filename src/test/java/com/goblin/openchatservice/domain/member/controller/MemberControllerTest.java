package com.goblin.openchatservice.domain.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.openchatservice.ControllerTestSupport;
import com.goblin.openchatservice.domain.member.mock.FakeMemberDto;
import com.goblin.openchatservice.domain.member.model.CreateMember;
import com.goblin.openchatservice.domain.member.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MemberControllerTest extends ControllerTestSupport {

    private final String BASE_URL = "/api/members";

    @DisplayName("멤버를 생성한다.")
    @Test
    void create() throws Exception {

        CreateMember createMember = FakeMemberDto.createMember();
        Member member = Member.from(createMember, createMember.password());

        when(memberService.create(any(CreateMember.class))).thenReturn(member);

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createMember))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("email 필드는 이메일 형식이어야 한다.")
    @Test
    void create_invalidEmail() throws Exception {
        CreateMember createMember = FakeMemberDto.customCreateMember(
                "testName",
                "testEmail",
                "testPassword"
        );

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createMember))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validator.email").value("이메일 형식이 맞지 않습니다."));
    }

    @DisplayName("password는 8글자 이상 16글자 이하여야 한다.")
    @Test
    void create_invalidPassword() throws Exception {
        CreateMember createMember = FakeMemberDto.customCreateMember(
                "testName",
                "testEmail@naver.com",
                "testPassword12341234"
        );

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createMember))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validator.password").value("비밀번호는 8글자 이상 16글자 이하여야 합니다."));
    }


}