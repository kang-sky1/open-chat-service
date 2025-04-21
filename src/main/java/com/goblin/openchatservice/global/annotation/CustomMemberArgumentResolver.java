package com.goblin.openchatservice.global.annotation;

import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class CustomMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CustomAuthenticationPrincipal.class) != null
                && parameter.getParameterType().isAssignableFrom(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            return null;
        }

        Long memberId = (Long)authentication.getPrincipal();

        Member byId = memberRepository.findById(memberId);
        return byId;
    }
}
