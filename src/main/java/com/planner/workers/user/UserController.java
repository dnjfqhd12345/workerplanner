package com.planner.workers.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(),
                    userCreateForm.getNickname(), userCreateForm.getBirthday(), userCreateForm.getPassword1(), userCreateForm.getPayday());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/approve")
    public String approve(Model model, Principal principal) {
        List<SiteUser> siteUsers = this.userService.getGuestUsers("guest");
        model.addAttribute("pendingUsers", siteUsers);
        return "admin_page";
    }

    @PostMapping("/approve/{id}")
    public String approveUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.approve(id);
            redirectAttributes.addFlashAttribute("successMessage", "승인 처리가 완료되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류가 발생되었습니다.");
        }
        return "redirect:/user/approve"; // 관리자 페이지 GET 주소
    }

    @PostMapping("/reject/{id}")
    public String rejectUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.reject(id);
            redirectAttributes.addFlashAttribute("successMessage", "거절 처리가 완료되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류가 발생되었습니다.");
        }
        return "redirect:/user/approve";
    }
}