package com.planner.workers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPlanDTO {
    private Integer planId;
    private String planName;
    private String category;
    private long dDay;
}
