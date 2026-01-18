package com.planner.workers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String nickname, LocalDate birthday, String password, Integer payday) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setBirthday(birthday);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        user.setPayday(payday);
        user.setMemberTier("guest");
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        return siteUser.get();
    }

    public List<SiteUser> getGuestUsers(String memberTier){
        List<SiteUser> guestUsers = userRepository.findByMemberTier(memberTier);
        return guestUsers;
    }

    public void approve(Integer id){
        SiteUser siteUser = userRepository.findSiteUserById(id);
        siteUser.setMemberTier("member");
        userRepository.save(siteUser);
    }

    public void reject(Integer id){
        SiteUser siteUser = userRepository.findSiteUserById(id);
        userRepository.delete(siteUser);
    }

}
