package com.goblin.openchatservice.domain.member.model;

public record Member(
    Long id,
    String name,
    String email,
    String password
) {

    public static Member from(CreateMember createMember, String password) {
        return new Member(
            null,
            createMember.name(),
            createMember.email(),
            password
        );
    }

    public void validatePassword(String inputPassword) {
        boolean isNotPassword = !password.equals(inputPassword);
        if (isNotPassword) {
            throw new IllegalArgumentException("올바르지 않은 패스워드입니다.");
        }
    }
}
