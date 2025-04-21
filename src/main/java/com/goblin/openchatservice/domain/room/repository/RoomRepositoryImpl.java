package com.goblin.openchatservice.domain.room.repository;

import com.goblin.openchatservice.domain.room.model.Room;
import com.goblin.openchatservice.domain.room.service.port.RoomRepository;
import java.util.List;
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
        return roomJpaRepository.findById(id)
                .map(RoomEntity::toModel)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
    }

    @Override
    public List<Room> findAll() {
        return roomJpaRepository.findAll().stream().map(RoomEntity::toModel).toList();
    }
}
