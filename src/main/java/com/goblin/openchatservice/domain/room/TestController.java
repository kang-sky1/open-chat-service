package com.goblin.openchatservice.domain.room;

import com.goblin.openchatservice.domain.member.model.Member;
import com.goblin.openchatservice.domain.room.model.CreateRoom;
import com.goblin.openchatservice.domain.room.service.RoomService;
import com.goblin.openchatservice.global.annotation.CustomAuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final RoomService roomService;

    @GetMapping("/test")
    public void test(
            @CustomAuthenticationPrincipal Member member
    ) {
        CreateRoom createRoom = new CreateRoom("aa", "aaa", 4);
        roomService.createRoom(member,createRoom);
    }
}
