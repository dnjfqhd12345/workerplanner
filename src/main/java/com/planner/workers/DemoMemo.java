package com.planner.workers;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_memo")
public class DemoMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memoText;
}
