package com.goblin.openchatservice.domain.room.mock;

import com.goblin.openchatservice.domain.room.model.CreateRoom;
import com.goblin.openchatservice.domain.room.model.Room;

public class FakeRoomDto {

    public static CreateRoom createRoom(
            String name,
            String description,
            int maxMember
    ) {
        return new CreateRoom(name, description, maxMember);
    }

    public static CreateRoom createRoom() {
        return new CreateRoom("testRoom", "test room", 4);
    }

    public static Room room() {
        return new Room(1L, "testRoom", "test room", 4, null);
    }
}
