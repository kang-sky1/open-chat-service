package com.goblin.openchatservice.domain.member.mock;

import com.goblin.openchatservice.domain.member.model.CreateMember;
import com.goblin.openchatservice.domain.member.model.LoginMember;

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

    public static LoginMember loginMember() {
        return new LoginMember(
                "testEmail@naver.com",
                "testPassword"
        );
    }

    public static LoginMember customLoginMember(
            String email,
            String password
    ){
        return new LoginMember(
                email,
                password
        );
    }
}
