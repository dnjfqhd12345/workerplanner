package com.planner.workers.plan;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlanForm {
    @NotEmpty(message = "계획 이름은 필수항목입니다.")
    private String planName;

    @NotEmpty(message = "계획 내용은 필수항목입니다.")
    private String planContent;

    @NotNull(message = "계획 만기일은 필수항목입니다.")
    private LocalDate dueDate;

    @NotEmpty(message = "카테고리를 선택해주세요.")
    private String category;
}
