package com.planner.workers.plan;

import com.planner.workers.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
    List<Plan> findAllByUserName(SiteUser userName);

    @Query("SELECT p FROM Plan p " +
            "WHERE p.userName = :user " +
            "AND p.dueDate >= :today " +
            "AND p.finished = false " +
            "ORDER BY p.dueDate ASC")
    List<Plan> findUpcomingPlans(@Param("user") SiteUser siteUser, @Param("today")LocalDate today);

    @Query("SELECT p FROM Plan p " +
            "WHERE p.userName = :user " +
            "AND p.dueDate = (" +
            "   SELECT MAX(p2.dueDate) FROM Plan p2 " +
            "   WHERE p2.userName = :user " +
            "   AND p2.dueDate < :today " +
            ") " +
            "ORDER BY p.dueDate DESC"
    )
    List<Plan> findRecentPlans(@Param("user") SiteUser siteUser, @Param("today")LocalDate today);
}