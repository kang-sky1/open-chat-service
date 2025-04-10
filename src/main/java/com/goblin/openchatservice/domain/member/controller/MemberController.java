package com.goblin.openchatservice.domain.member.controller;

import com.goblin.openchatservice.domain.member.model.CreateMember;
import com.goblin.openchatservice.domain.member.model.LoginMember;
import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.member.service.MemberService;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> create(
            @RequestBody @Valid CreateMember createMember
    ) {
        return ResponseEntity
                .status(201)
                .body(memberService.create(createMember));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody @Valid LoginMember loginMember
    ) {
        String token = memberService.login(loginMember);
        ResponseCookie cookie = ResponseCookie.from("Authorization", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")
                .build();

        return ResponseEntity.status(200)
                .header("Set-Cookie", cookie.toString())
                .build();
    }


}
