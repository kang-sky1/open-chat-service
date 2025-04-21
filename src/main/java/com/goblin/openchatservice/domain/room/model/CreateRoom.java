package com.goblin.openchatservice.domain.room.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateRoom(
        @NotBlank(message = "필수 입력값입니다.")
        String name,
        String description,
        @Min(value = 1, message = "최솟값이 1입니다.")
        int maxMember
) {
}
