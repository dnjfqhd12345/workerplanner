package com.planner.workers.plan;

import com.planner.workers.user.SiteUser;
import com.planner.workers.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/plan")
@RequiredArgsConstructor
@Controller
public class PlanController {

    private final PlanService planService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, Principal principal){
        SiteUser siteUser = userService.getUser(principal.getName());
        List<Plan> planList = this.planService.getPlanList(siteUser);
        model.addAttribute("planList", planList);
        return "plan_list";
    }

    @GetMapping(value = "/list/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Plan plan = this.planService.getPlan(id);
        model.addAttribute("plan", plan);
        return "plan_detail";
    }

    @GetMapping("/create")
    public String planCreate(PlanForm planForm) {
        return "plan_write";
    }

    @PostMapping("/create")
    public String planCreate(@Valid PlanForm planForm, Principal principal, BindingResult bindingResult ){
        // TODO 플랜을 저장합니다.
        if(bindingResult.hasErrors()){
            return "plan_write";
        }
        SiteUser siteUser = userService.getUser(principal.getName());
        planService.create(planForm.getPlanName(),planForm.getPlanContent(),planForm.getDueDate(),false,planForm.getCategory(),siteUser);
        return "redirect:/plan/list"; // 플랜 저장 후 플랜 목록으로
    }

    @PostMapping("/finish/{id}")
    public String toggleFinish(@PathVariable Integer id, @RequestParam boolean finished){

        if(finished){
            planService.togglePlan(id, finished);
        } else {
            planService.togglePlan(id, finished);
        }

        return "redirect:/plan/list";
    }

    @GetMapping("/modify/{id}")
    public String getPlanModify(@PathVariable Integer id, Principal principal, Model model){
        Plan plan = planService.getPlan(id);

        String loginUserId = principal.getName();

        /*
        if(!plan.getUserName().equals(loginUserId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }*/

        model.addAttribute("plan", plan);
        return "plan_modify";
    }

    @GetMapping("/detail/{id}")
    public String planDetail(@PathVariable Integer id, Principal principal, Model model){
        Plan plan = planService.getPlan(id);

        String loginUserId = principal.getName();

        /*
        if(!plan.getUserName().equals(loginUserId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }*/

        model.addAttribute("plan", plan);
        return "plan_detail";
    }

    @PostMapping("/modify/{id}")
    public String postPlanModify(@PathVariable Integer id,@ModelAttribute Plan plan, Principal principal, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "plan_modify";
        }
        // SiteUser siteUser = userService.getUser(principal.getName());
        planService.modify(id,plan);
        return "redirect:/plan/list"; // 플랜 수정 후 플랜 목록으로
    }

    @PostMapping("/delete/{id}")
    public String planDelete(@PathVariable Integer id){

        planService.delete(id);
        return "redirect:/plan/list";
    }

}
