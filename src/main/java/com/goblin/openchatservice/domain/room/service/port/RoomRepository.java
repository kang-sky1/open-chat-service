package com.goblin.openchatservice.domain.room.service.port;

import com.goblin.openchatservice.domain.room.model.Room;
import java.util.List;

public interface RoomRepository {

    Room save(Room room);
    Room findById(Long id);

    List<Room> findAll();
}
