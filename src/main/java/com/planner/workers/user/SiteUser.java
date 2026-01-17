package com.planner.workers.user;

import com.planner.workers.memo.Memo;
import com.planner.workers.plan.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    private LocalDate birthday;

    private Integer payday;

    // Guest = 준회원, Member = 정회원
    private String memberTier;

    @OneToMany(mappedBy = "userName", cascade = CascadeType.REMOVE)
    private List<Plan> planList;

    @OneToMany(mappedBy = "userName", cascade = CascadeType.REMOVE)
    private List<Memo> memoList;
}