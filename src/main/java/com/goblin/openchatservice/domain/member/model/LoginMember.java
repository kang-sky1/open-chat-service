package com.goblin.openchatservice.domain.member.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginMember(
        @NotBlank(message = "필수 입력값입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식이 맞지 않습니다.")
        String email,
        @NotBlank(message = "필수 입력값입니다.")
        @Size(min = 8, max = 16, message = "비밀번호는 8글자 이상 16글자 이하여야 합니다.")
        String password
) {
}
