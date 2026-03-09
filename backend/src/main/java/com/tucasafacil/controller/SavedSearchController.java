package com.tucasafacil.controller;

import com.tucasafacil.dto.SavedSearchDto;
import com.tucasafacil.service.SavedSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-searches")
public class SavedSearchController {

    private final SavedSearchService savedSearchService;

    public SavedSearchController(SavedSearchService savedSearchService) {
        this.savedSearchService = savedSearchService;
    }

    @PostMapping
    public ResponseEntity<SavedSearchDto> saveSearch(@RequestBody SavedSearchDto searchDto) {
        SavedSearchDto saved = savedSearchService.saveSearch(searchDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<SavedSearchDto>> getSavedSearches() {
        List<SavedSearchDto> searches = savedSearchService.getSavedSearches();
        return ResponseEntity.ok(searches);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedSearch(@PathVariable Long id) {
        savedSearchService.deleteSavedSearch(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/execute")
    public ResponseEntity<List<Object>> executeSavedSearch(@PathVariable Long id) {
        List<Object> results = savedSearchService.executeSavedSearch(id);
        return ResponseEntity.ok(results);
    }
}