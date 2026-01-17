package com.planner.workers;

import com.planner.workers.dto.UserPlanDTO;
import com.planner.workers.user.SiteUser;
import com.planner.workers.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;
    private final MainService mainService;

    @GetMapping("/sbb")
    @ResponseBody
    public String index(){
        return "index";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String mainList(Model model, Principal principal){
        SiteUser siteUser = this.userService.getUser(principal.getName());
        List<UserPlanDTO> userPlanDTOList = this.mainService.getUserPlans(siteUser);
        String dDays = this.mainService.getPayday(siteUser);
        String achievementRate = this.mainService.getAchievementRate(siteUser);
        model.addAttribute("name", siteUser.getNickname());
        model.addAttribute("userPlanDTOList", userPlanDTOList);
        model.addAttribute("dDays", dDays);
        model.addAttribute("achievementRate", achievementRate);
        return "main_page";
    }
}
