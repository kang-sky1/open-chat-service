package com.goblin.openchatservice.domain.member.mock;

import com.goblin.openchatservice.domain.member.model.CreateMember;

public class FakeMemberDto {


    public static CreateMember createMember() {
        return new CreateMember(
                "testName",
                "testEmail@naver.com",
                "testPassword"
        );
    }

    public static CreateMember customCreateMember(
            String name,
            String email,
            String password
    ){
        return new CreateMember(
                name,
                email,
                password
        );
    }
}
