package com.goblin.openchatservice.domain.room.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.openchatservice.ControllerTestSupport;
import com.goblin.openchatservice.domain.room.mock.FakeRoomDto;
import com.goblin.openchatservice.domain.room.model.CreateRoom;
import com.goblin.openchatservice.domain.room.model.Room;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class RoomControllerTest extends ControllerTestSupport {

    private final String BASE_URL = "/api/rooms";


    @DisplayName("방을 생성한다.")
    @Test
    void create() throws Exception {
        CreateRoom room = FakeRoomDto.createRoom();

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(room))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("방 이름은 필수값이다.")
    @Test
    void create_invalidName() throws Exception {
        CreateRoom room = FakeRoomDto.createRoom("", "test", 0);

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(room))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validator.name").value("필수 입력값입니다."));
    }

    @DisplayName("방의 참여인원 최솟값은 1이다.")
    @Test
    void create_invalidMaxMember() throws Exception {
        CreateRoom room = FakeRoomDto.createRoom("test", "", 0);

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(room))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validator.maxMember").value("최솟값이 1입니다."));
    }

    @DisplayName("방을 전체 조회한다.")
    @Test
    void findByAll() throws Exception {
        List<Room> rooms = List.of(
                FakeRoomDto.room()
        );

        when(roomService.findAll()).thenReturn(rooms);

        mockMvc.perform(
                        get(BASE_URL)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(rooms.get(0).name()));
    }

    @DisplayName("방을 단일 조회한다.")
    @Test
    void findById() throws Exception {
        Room room = FakeRoomDto.room();
        when(roomService.findById(room.id())).thenReturn(room);

        mockMvc.perform(
                        get(BASE_URL + "/" + room.id())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(room.name()));
    }


}