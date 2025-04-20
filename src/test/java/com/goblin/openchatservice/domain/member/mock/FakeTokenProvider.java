package com.goblin.openchatservice.domain.member.mock;

import com.goblin.openchatservice.domain.auth.TokenProvider;

public class FakeTokenProvider implements TokenProvider {

    @Override
    public String createToken(Long memberId) {
        return "Bearer TestToken" + memberId;
    }

    @Override
    public String getSubjectFromToken(String token) {
        return "";
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }
}
