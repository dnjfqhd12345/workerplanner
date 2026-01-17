package com.planner.workers.plan;

import com.planner.workers.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String planName;

    @Column(columnDefinition = "TEXT")
    private String planContent;

    private LocalDate dueDate;

    private LocalDate postDate;

    private boolean finished;

    private String category;

    @ManyToOne
    private SiteUser userName;

    // ✅ 오늘과 dueDate 차이(일 수)
    public long getDday() {
        if (dueDate == null) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
    }

    // ✅ 화면에 보여줄 텍스트
    public String getDdayText() {
        if (finished) return "완수";
        if (dueDate == null) return "-";

        long dday = getDday();
        if (dday > 0) return "D-" + dday;
        if (dday == 0) return "D-DAY";
        return "D+" + Math.abs(dday); // 기한 지남
    }

}
