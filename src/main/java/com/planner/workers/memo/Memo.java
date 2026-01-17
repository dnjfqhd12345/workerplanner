package com.planner.workers.memo;

import com.planner.workers.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String memoName;

    @Column(columnDefinition = "TEXT")
    private String memoContent;

    private LocalDate postDate;

    @ManyToOne
    private SiteUser userName;


}
