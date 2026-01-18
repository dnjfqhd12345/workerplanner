package com.planner.workers.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String username = authentication.getName();
        SiteUser user = userRepository.findByUsername(username).orElseThrow();

        // ✅ Guest면: 세션 무효화하고 로그인 페이지로 돌려보내기 + pending 파라미터
        if (!"Member".equalsIgnoreCase(user.getMemberTier()) && !"admin".equalsIgnoreCase(username)) {
            request.getSession().invalidate();
            response.sendRedirect("/user/login?pending");
            return;
        }

        // ✅ Member(또는 admin)면 정상 진입
        response.sendRedirect("/");
    }

}
