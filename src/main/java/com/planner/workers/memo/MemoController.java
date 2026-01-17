package com.planner.workers.memo;

import com.planner.workers.plan.Plan;
import com.planner.workers.plan.PlanService;
import com.planner.workers.user.SiteUser;
import com.planner.workers.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/memo")
@RequiredArgsConstructor
@Controller
public class MemoController {

    private final MemoService memoService;
    private final UserService userService;
    private final PlanService planService;

    @GetMapping("/list")
    public String list(Model model, Principal principal){
        SiteUser siteUser = userService.getUser(principal.getName());
        List<Memo> memoList = this.memoService.getMemoList(siteUser);
        model.addAttribute("memoList", memoList);
        return "memo_list";
    }

    @GetMapping(value = "/list/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Memo memo = this.memoService.getMemo(id);
        model.addAttribute("memo", memo);
        return "memo_detail";
    }

    @GetMapping("/create")
    public String memoCreate(MemoForm memoForm){
        return "memo_write";
    }

    @PostMapping("/create")
    public String memoCreate(@Valid MemoForm memoForm, Principal principal, BindingResult bindingResult){
        // TODO 메모를 저장합니다.
        if(bindingResult.hasErrors()){
            return "memo_write";
        }
        SiteUser siteUser = userService.getUser(principal.getName());
        memoService.create(memoForm.getMemoName(),memoForm.getMemoContent(),siteUser);
        return "redirect:/memo/list"; // 메모 저장 후 메모 목록으로
    }

    @GetMapping("/modify/{id}")
    public String getMemoModify(@PathVariable Integer id, Principal principal, Model model){
        Memo memo = memoService.getMemo(id);

        model.addAttribute("memo", memo);
        return "memo_modify";
    }

    @GetMapping("/detail/{id}")
    public String memoDetail(@PathVariable Integer id, Principal principal, Model model){
        Memo memo = memoService.getMemo(id);

        model.addAttribute("memo",memo);
        return "memo_detail";
    }

    @PostMapping("/modify/{id}")
    public String postMemoModify(@PathVariable Integer id, @ModelAttribute Memo memo, Principal principal, BindingResult bindingResult){
        memoService.modify(id,memo);
        return "redirect:/memo/list"; // 메모 수정 후 메모 목록으로
    }

    @PostMapping("/delete/{id}")
    public String memoDelete(@PathVariable Integer id){
        memoService.delete(id);
        return "redirect:/memo/list";
    }


}
