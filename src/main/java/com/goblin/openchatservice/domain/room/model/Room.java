package com.goblin.openchatservice.domain.room.model;

import com.goblin.openchatservice.domain.member.model.Member;

public record Room(
        Long id,
        String name,
        String description,
        int maxMember,
        Member owner
) {

    public static Room from(CreateRoom createRoom, Member owner) {
        return new Room(
                null,
                createRoom.name(),
                createRoom.description(),
                createRoom.maxMember(),
                owner
        );
    }

    public void validateOwner(Member member){
        boolean isNotOwner = !owner.equals(member);
        if(isNotOwner){
            throw new IllegalArgumentException("해당 오너가 아닙니다.");
        }
    }

}
