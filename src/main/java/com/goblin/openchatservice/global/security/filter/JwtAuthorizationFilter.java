package com.goblin.openchatservice.global.security.filter;

import com.goblin.openchatservice.domain.auth.TokenProvider;
import com.goblin.openchatservice.global.security.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Cookie cookie = getCookie(request.getCookies());
            if (cookie == null) {
                filterChain.doFilter(request, response);
                return;
            }
            jwtProvider.validateToken(cookie.getValue());
            authenticate(cookie);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(Cookie cookie) {
        String token = cookie.getValue();
        Long memberId = Long.valueOf(jwtProvider.getSubjectFromToken(token));
        Authentication authentication = new UsernamePasswordAuthenticationToken(memberId, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Cookie getCookie(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "Authorization")) {
                return cookie;
            }
        }

        return null;
    }
}
