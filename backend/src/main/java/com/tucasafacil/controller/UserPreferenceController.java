package com.tucasafacil.controller;

import com.tucasafacil.entity.User;
import com.tucasafacil.entity.UserPreference;
import com.tucasafacil.repository.UserPreferenceRepository;
import com.tucasafacil.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class UserPreferenceController {

    private final UserPreferenceRepository userPreferenceRepository;
    private final CurrentUserService currentUserService;

    public UserPreferenceController(UserPreferenceRepository userPreferenceRepository,
                                    CurrentUserService currentUserService) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<UserPreference> getPreferences() {
        User currentUser = getCurrentUser();
        UserPreference prefs = userPreferenceRepository.findByUserId(currentUser.getId()).orElse(null);
        if (prefs == null) {
            prefs = new UserPreference();
            prefs.setUser(currentUser);
        }
        return ResponseEntity.ok(prefs);
    }

    @PutMapping
    public ResponseEntity<UserPreference> updatePreferences(@RequestBody UserPreference preferences) {
        User currentUser = getCurrentUser();
        UserPreference existing = userPreferenceRepository.findByUserId(currentUser.getId()).orElse(null);
        if (existing != null) {
            preferences.setId(existing.getId());
        }
        preferences.setUser(currentUser);
        UserPreference saved = userPreferenceRepository.save(preferences);
        return ResponseEntity.ok(saved);
    }

    private User getCurrentUser() {
        return currentUserService.getCurrentUserOrDemo();
    }
}
