package com.goblin.openchatservice.domain.room.controller;


import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.room.model.CreateRoom;
import com.goblin.openchatservice.domain.room.model.Room;
import com.goblin.openchatservice.domain.room.service.RoomService;
import com.goblin.openchatservice.global.annotation.CustomAuthenticationPrincipal;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(
            @CustomAuthenticationPrincipal Member member,
            @RequestBody @Valid CreateRoom room
    ) {
        Room result = roomService.createRoom(member, room);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoom(
            @PathVariable Long roomId
    ){
        Room result = roomService.findById(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll());
    }

}
