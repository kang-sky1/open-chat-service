package com.goblin.openchatservice.domain.room.model;

public record CreateRoom(
        String name,
        String description,
        int maxMember
) {
}
