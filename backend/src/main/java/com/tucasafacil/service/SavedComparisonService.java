package com.tucasafacil.service;

import com.tucasafacil.dto.SavedComparisonDto;
import com.tucasafacil.entity.SavedComparison;
import com.tucasafacil.entity.User;
import com.tucasafacil.repository.SavedComparisonRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedComparisonService {

    private final SavedComparisonRepository savedComparisonRepository;
    private final CurrentUserService currentUserService;

    public SavedComparisonService(SavedComparisonRepository savedComparisonRepository, CurrentUserService currentUserService) {
        this.savedComparisonRepository = savedComparisonRepository;
        this.currentUserService = currentUserService;
    }

    public SavedComparisonDto saveComparison(SavedComparisonDto comparisonDto) {
        User currentUser = getCurrentUser();

        SavedComparison savedComparison = new SavedComparison();
        savedComparison.setUser(currentUser);
        savedComparison.setName(comparisonDto.getName());
        savedComparison.setDescription(comparisonDto.getDescription());
        savedComparison.setPropertyIds(
                comparisonDto.getPropertyIds().stream()
                        .map(Long::intValue)
                        .toArray(Integer[]::new)
        );

        SavedComparison saved = savedComparisonRepository.save(savedComparison);

        return new SavedComparisonDto(saved.getId(), saved.getName(), saved.getDescription(),
                Arrays.stream(saved.getPropertyIds()).map(Integer::longValue).collect(Collectors.toList()),
                saved.getCreatedAt());
    }

    public List<SavedComparisonDto> getSavedComparisons() {
        User currentUser = getCurrentUser();
        return savedComparisonRepository.findByUserId(currentUser.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SavedComparisonDto getSavedComparison(Long comparisonId) {
        User currentUser = getCurrentUser();
        SavedComparison comparison = savedComparisonRepository.findById(comparisonId)
                .filter(c -> c.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Comparación no encontrada"));

        return convertToDto(comparison);
    }

    public void deleteSavedComparison(Long comparisonId) {
        User currentUser = getCurrentUser();
        SavedComparison comparison = savedComparisonRepository.findById(comparisonId)
                .filter(c -> c.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Comparación no encontrada"));
        savedComparisonRepository.delete(comparison);
    }

    private SavedComparisonDto convertToDto(SavedComparison comparison) {
        return new SavedComparisonDto(comparison.getId(), comparison.getName(), comparison.getDescription(),
                Arrays.stream(comparison.getPropertyIds()).map(Integer::longValue).collect(Collectors.toList()),
                comparison.getCreatedAt());
    }

    private User getCurrentUser() {
        return currentUserService.getCurrentUserOrDemo();
    }
}
