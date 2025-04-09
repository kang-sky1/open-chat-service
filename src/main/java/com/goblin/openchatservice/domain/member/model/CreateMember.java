package com.goblin.openchatservice.domain.member.model;

public record CreateMember(
    String name,
    String email,
    String password
) {

}
