package com.planner.workers.plan;

import com.planner.workers.DataNotFoundException;
import com.planner.workers.user.SiteUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public List<Plan> getPlanList(SiteUser siteUser) {
        return this.planRepository.findAllByUserName(siteUser);
    }

    public Plan getPlan(Integer id){
        Optional<Plan> plan = this.planRepository.findById(id);
        if(plan.isPresent()){
            return plan.get();
        } else {
            throw new DataNotFoundException("plan not found");
        }
    }

    @Transactional
    public void togglePlan(Integer id, boolean finished){
        Optional<Plan> plan = this.planRepository.findById(id);
        if(plan.isPresent()){
            if(finished){ // 현재 완수 상태
                plan.get().setFinished(false);
            } else { // 현재 미완수 상태
                plan.get().setFinished(true);
            }
        } else {
            throw new DataNotFoundException("plan not found");
        }
    }

    public void create(String planName, String planContent, LocalDate dueDate, boolean finished, String category, SiteUser username){
        Plan plan = new Plan();
        plan.setPlanName(planName);
        plan.setPlanContent(planContent);
        plan.setDueDate(dueDate);
        plan.setPostDate(LocalDate.now());
        plan.setFinished(finished);
        plan.setCategory(category);
        plan.setUserName(username);
        planRepository.save(plan);
    }

    public void modify(Integer id,Plan plan){
        Plan modifyPlan = getPlan(id);
        modifyPlan.setPlanName(plan.getPlanName());
        modifyPlan.setPlanContent(plan.getPlanContent());
        modifyPlan.setDueDate(plan.getDueDate());
        modifyPlan.setCategory(plan.getCategory());
        planRepository.save(modifyPlan);
    }

    public void delete(Integer id){
        Optional<Plan> plan = planRepository.findById(id);
        this.planRepository.delete(plan.get());
    }

}