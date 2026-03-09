package com.tucasafacil.service;

import com.tucasafacil.entity.User;
import com.tucasafacil.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrentUserService {

    private static final String DEMO_USERNAME = "demo";
    private static final String DEMO_EMAIL = "demo@tucasafacil.local";
    private static final String DEMO_PASSWORD = "demo1234";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CurrentUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getCurrentUserOrDemo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            if (username != null && !username.isBlank() && !"anonymousUser".equalsIgnoreCase(username)) {
                return userRepository.findByUsername(username).orElseGet(this::getOrCreateDemoUser);
            }
        }

        return getOrCreateDemoUser();
    }

    private User getOrCreateDemoUser() {
        return userRepository.findByUsername(DEMO_USERNAME).orElseGet(() -> {
            User user = new User();
            user.setUsername(DEMO_USERNAME);
            user.setEmail(DEMO_EMAIL);
            user.setPassword(passwordEncoder.encode(DEMO_PASSWORD));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }
}
