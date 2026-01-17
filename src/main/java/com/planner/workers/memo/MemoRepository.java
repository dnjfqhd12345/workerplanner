package com.planner.workers.memo;

import com.planner.workers.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Integer> {
    List<Memo> findAllByUserName(SiteUser userName);
}