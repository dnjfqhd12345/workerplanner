package com.planner.workers;

import com.planner.workers.dto.UserPlanDTO;
import com.planner.workers.memo.MemoRepository;
import com.planner.workers.plan.Plan;
import com.planner.workers.plan.PlanRepository;
import com.planner.workers.user.SiteUser;
import com.planner.workers.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainService {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final MemoRepository memoRepository;

    // TODO 월급날 D-Day 보여주기
    public String getPayday(SiteUser siteUser){
        LocalDate nowDate = LocalDate.now();
        int payday = siteUser.getPayday();

        // 이번 달 월급날
        LocalDate payDate = nowDate.withDayOfMonth(payday);

        // 이미 지났으면 다음 달 월급날로
        if(payDate.isBefore(nowDate)){
            payDate = payDate.plusMonths(1);
        }

        // D-Day 계산
        long dDay = ChronoUnit.DAYS.between(nowDate, payDate);

        return String.valueOf(dDay);
    }

    // TODO 최근 계획 달성률 보여주기
    public String getAchievementRate(SiteUser siteUser){
        LocalDate nowDate = LocalDate.now();

        // 오늘 이전인 플랜 중에서 최신 계획들 가져오기
        List<Plan> planList = planRepository.findRecentPlans(siteUser, nowDate);

        if(planList.isEmpty()){
            return "0";
        }

        int totalCount = planList.size();
        long finishedCount = planList.stream().filter(Plan::isFinished).count();
        double rate = ((double) finishedCount / totalCount) * 100;

        // 소수점 처리
        return String.format("%.1f", rate);
    }

    // TODO D-Day 포함해서 앞으로의 남은 계획들 보여주기
    public List<UserPlanDTO> getUserPlans(SiteUser siteUser){
        LocalDate today = LocalDate.now();

        // 마감일이 오늘 이후인 계획들만 가져오기
        List<Plan> planList = planRepository.findUpcomingPlans(siteUser, today);

        return planList.stream()
                .map(p -> {
                    long dDay = ChronoUnit.DAYS.between(today, p.getDueDate());

                    return new UserPlanDTO(
                            p.getId(),
                            p.getPlanName(),
                            p.getCategory(),
                            dDay
                    );
                })
                .toList();
    }
}
