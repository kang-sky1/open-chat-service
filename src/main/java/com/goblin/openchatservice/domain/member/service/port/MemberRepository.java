package com.goblin.openchatservice.domain.member.service.port;

import com.goblin.openchatservice.domain.member.model.Member;

public interface MemberRepository {

    Member save(Member member);
    Member findById(Long id);
    Member findByEmail(String email);
}
