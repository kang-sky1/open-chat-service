package com.goblin.openchatservice;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.openchatservice.domain.member.controller.MemberController;
import com.goblin.openchatservice.domain.member.mock.FakeMemberRepository;
import com.goblin.openchatservice.domain.member.service.MemberService;
import com.goblin.openchatservice.global.annotation.CustomMemberArgumentResolver;
import com.goblin.openchatservice.global.exception.GlobalExceptionHandler;
import com.goblin.openchatservice.global.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(
        controllers = {
                MemberController.class
        },
        excludeAutoConfiguration = SecurityAutoConfiguration.class
)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new GlobalExceptionHandler())
            .build();
    @Autowired
    protected ObjectMapper objectMapper;
    @MockitoBean
    protected MemberService memberService;

    @MockitoBean
    CustomMemberArgumentResolver customMemberArgumentResolver = new CustomMemberArgumentResolver(
            new FakeMemberRepository());

    @MockitoBean
    protected JwtProvider jwtProvider = new JwtProvider(
            "4714559cf0bd05573220c2b5f0e4f55cb2668679de7e5e5db7afd01c5c3ea0dac72f9e4fb717758e381d7af50b335bdb8768cd95a4deafbb7fb74ded50cc2e9d");


}
