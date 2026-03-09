package com.tucasafacil.controller;

import com.tucasafacil.entity.UserPreference;
import com.tucasafacil.repository.UserPreferenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class UserPreferenceController {

    private final UserPreferenceRepository userPreferenceRepository;

    public UserPreferenceController(UserPreferenceRepository userPreferenceRepository) {
        this.userPreferenceRepository = userPreferenceRepository;
    }

    @GetMapping
    public ResponseEntity<UserPreference> getPreferences() {
        UserPreference prefs = userPreferenceRepository.findFirstByOrderByIdAsc();
        if (prefs == null) {
            prefs = new UserPreference();
        }
        return ResponseEntity.ok(prefs);
    }

    @PutMapping
    public ResponseEntity<UserPreference> updatePreferences(@RequestBody UserPreference preferences) {
        UserPreference existing = userPreferenceRepository.findFirstByOrderByIdAsc();
        if (existing != null) {
            preferences.setId(existing.getId());
        }
        UserPreference saved = userPreferenceRepository.save(preferences);
        return ResponseEntity.ok(saved);
    }
}