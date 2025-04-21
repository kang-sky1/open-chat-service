package com.goblin.openchatservice;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.openchatservice.domain.member.controller.MemberController;
import com.goblin.openchatservice.domain.member.mock.FakeMemberRepository;
import com.goblin.openchatservice.domain.member.service.MemberService;
import com.goblin.openchatservice.domain.room.controller.RoomController;
import com.goblin.openchatservice.domain.room.service.RoomService;
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
                MemberController.class,
                RoomController.class
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
    protected RoomService roomService;
    @MockitoBean
    protected CustomMemberArgumentResolver customMemberArgumentResolver;


}
