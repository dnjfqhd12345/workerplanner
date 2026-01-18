package com.planner.workers;

import com.planner.workers.user.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, LoginSuccessHandler loginSuccessHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 로그인 페이지, 회원가입, 정적 리소스는 허용
                        .requestMatchers(
                                "/user/login",
                                "/user/signup",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        .anyRequest().hasAnyRole("MEMBER","ADMIN")
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")
                        .successHandler(loginSuccessHandler)
                )
                .logout((logout) -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request,response,accessDeniedException) -> {
                            // Guest등 "권한 부족"으로 403 발생 시
                            // 세션을 지우고 로그인 페이지로 돌려보내기 (메시지 파라미터 포함)
                            request.getSession().invalidate();
                            response.sendRedirect("/user/login?pending");
                        }))
                ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
