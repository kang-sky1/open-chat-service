package com.goblin.openchatservice.domain.room.service;

import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.room.model.CreateRoom;
import com.goblin.openchatservice.domain.room.model.Room;
import com.goblin.openchatservice.domain.room.service.port.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public Room createRoom(Member owner, CreateRoom createRoom) {
        Room room = Room.from(createRoom, owner);
        return roomRepository.save(room);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId);
    }
}
