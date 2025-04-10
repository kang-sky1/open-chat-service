package com.goblin.openchatservice.domain.member.mock;

import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.member.service.port.MemberRepository;
import java.util.ArrayList;
import java.util.List;

public class FakeMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();
    private Long id = 1L;

    @Override
    public Member save(Member member) {
        if(member.id() == null){
            Member result = new Member(id++, member.name(), member.email(), member.password());
            members.add(result);
            return result;
        }
        return member;
    }

    @Override
    public Member findById(Long id) {
        return members.stream().filter(member -> member.id().equals(id)).findFirst().orElse(null);
    }
}
