package com.goblin.openchatservice.domain.room.repository;

import com.goblin.openchatservice.domain.room.model.Room;
import com.goblin.openchatservice.domain.room.service.port.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RoomRepositoryImpl implements RoomRepository {

    private final RoomJpaRepository roomJpaRepository;

    @Override
    public Room save(Room room) {
        return roomJpaRepository.save(RoomEntity.from(room)).toModel();
    }

    @Override
    public Room findById(Long id) {
        return null;
    }
}
