package com.tucasafacil.controller;

import com.tucasafacil.dto.SavedComparisonDto;
import com.tucasafacil.service.SavedComparisonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-comparisons")
public class SavedComparisonController {

    private final SavedComparisonService savedComparisonService;

    public SavedComparisonController(SavedComparisonService savedComparisonService) {
        this.savedComparisonService = savedComparisonService;
    }

    @PostMapping
    public ResponseEntity<SavedComparisonDto> saveComparison(@RequestBody SavedComparisonDto comparisonDto) {
        SavedComparisonDto saved = savedComparisonService.saveComparison(comparisonDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<SavedComparisonDto>> getSavedComparisons() {
        List<SavedComparisonDto> comparisons = savedComparisonService.getSavedComparisons();
        return ResponseEntity.ok(comparisons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavedComparisonDto> getSavedComparison(@PathVariable Long id) {
        SavedComparisonDto comparison = savedComparisonService.getSavedComparison(id);
        return ResponseEntity.ok(comparison);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedComparison(@PathVariable Long id) {
        savedComparisonService.deleteSavedComparison(id);
        return ResponseEntity.ok().build();
    }
}