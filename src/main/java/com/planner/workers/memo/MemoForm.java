package com.planner.workers.memo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoForm {
    @NotEmpty(message = "메모 제목은 필수항목입니다.")
    private String memoName;

    @NotEmpty(message = "메모 내용은 필수항목입니다.")
    private String memoContent;

}
