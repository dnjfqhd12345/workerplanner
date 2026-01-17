package com.planner.workers.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;

    @NotNull(message = "생일은 필수항목입니다.")
    private LocalDate birthday;

    @NotNull(message = "월급날은 필수항목입니다.")
    @Min(value = 1, message = "월급날은 1~31 사이여야 합니다.")
    @Max(value = 31, message = "월급날은 1~31 사이여야 합니다.")
    private Integer payday;
}
