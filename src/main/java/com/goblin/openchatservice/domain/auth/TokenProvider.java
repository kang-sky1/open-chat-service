package com.goblin.openchatservice.domain.auth;

public interface TokenProvider {

    String createToken(Long memberId);
    String getSubjectFromToken(String token);
}

