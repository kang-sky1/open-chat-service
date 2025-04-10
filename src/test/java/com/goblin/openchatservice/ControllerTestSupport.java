package com.goblin.openchatservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.openchatservice.domain.member.controller.MemberController;
import com.goblin.openchatservice.domain.member.service.MemberService;
import com.goblin.openchatservice.global.exception.GlobalExceptionHandler;
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
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler()).build();
    @Autowired
    protected ObjectMapper objectMapper;
    @MockitoBean
    protected MemberService memberService;

}
