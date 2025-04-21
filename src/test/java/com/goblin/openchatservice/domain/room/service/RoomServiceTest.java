package com.goblin.openchatservice.domain.room.service;

import static org.assertj.core.api.Assertions.*;

import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.room.mock.FakeRoomRepository;
import com.goblin.openchatservice.domain.room.model.CreateRoom;
import com.goblin.openchatservice.domain.room.model.Room;
import com.goblin.openchatservice.domain.room.service.port.RoomRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomServiceTest {

    private RoomRepository roomRepository;
    private RoomService roomService;

    public RoomServiceTest() {
        this.roomRepository = new FakeRoomRepository();
        this.roomService = new RoomService(roomRepository);
    }

    @DisplayName("방을 생성한다.")
    @Test
    void create() {
        Member member = new Member(
                1L, "testName", "testEmail", "testPassword"
        );
        CreateRoom createRoom = new CreateRoom(
                "testRoomName", "test", 4
        );

        Room room = roomService.createRoom(member, createRoom);
        Room result = roomRepository.findById(room.id());

        assertThat(room)
                .extracting("id", "name", "description", "maxMember", "owner")
                .contains(result.id(), result.name(), result.description(), result.maxMember(), member);
    }

    @DisplayName("방을 전체 조회한다.")
    @Test
    void findAll() {
        List<Room> rooms = List.of(createRoom(), createRoom());
        List<Room> result = roomService.findAll();

        assertThat(result)
                .hasSize(rooms.size())
                .containsAll(rooms);

    }

    @DisplayName("방을 단일 조회한다.")
    @Test
    void findById() {
        Room room = createRoom();
        Room result = roomService.findById(room.id());

        assertThat(result)
                .isNotNull()
                .extracting("id", "name", "description", "maxMember")
                .contains(room.id(), room.name(), room.description(), room.maxMember());
    }

    private Room createRoom() {
        return roomRepository.save(Room.from(new CreateRoom("testRoom", "test room", 4),
                new Member(1L, "testName", "testEmail", "testPassword")));
    }
}