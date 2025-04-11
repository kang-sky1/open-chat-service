package com.goblin.openchatservice.domain.room.mock;

import com.goblin.openchatservice.domain.room.model.Room;
import com.goblin.openchatservice.domain.room.service.port.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FakeRoomRepository implements RoomRepository {

    List<Room> rooms = new ArrayList<>();
    Long id = 1L;

    @Override
    public Room save(Room room) {
        if (room.id() == null) {
            room = new Room(
                    id++,
                    room.name(),
                    room.description(),
                    room.maxMember(),
                    room.owner()
            );
            rooms.add(room);
            return room;
        }
        return room;
    }

    @Override
    public Room findById(Long id) {
        return rooms.stream()
                .filter(room -> room.id().equals(id))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("해당 방을 찾을 수 없습니다."));
    }
}
