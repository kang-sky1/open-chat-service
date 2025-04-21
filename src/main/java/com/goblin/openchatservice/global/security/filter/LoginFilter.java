package com.goblin.openchatservice.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.openchatservice.domain.auth.TokenProvider;
import com.goblin.openchatservice.domain.member.model.LoginMember;
import com.goblin.openchatservice.global.security.JwtProvider;
import com.goblin.openchatservice.global.security.service.MemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TokenProvider jwtProvider;

    public LoginFilter(AuthenticationManager authenticationManager, TokenProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginMember loginMember = objectMapper.readValue(request.getInputStream(), LoginMember.class);
            String email = loginMember.email();
            String password = loginMember.password();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                    password);
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();
        String token = jwtProvider.createToken(memberDetails.getId());
        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
    }
}
