package com.tucasafacil.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tucasafacil.dto.SavedSearchDto;
import com.tucasafacil.entity.SavedSearch;
import com.tucasafacil.entity.User;
import com.tucasafacil.repository.PropertyRepository;
import com.tucasafacil.repository.SavedSearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SavedSearchService {

    private final SavedSearchRepository savedSearchRepository;
    private final PropertyRepository propertyRepository;
    private final ObjectMapper objectMapper;
    private final CurrentUserService currentUserService;

    public SavedSearchService(SavedSearchRepository savedSearchRepository,
                              PropertyRepository propertyRepository,
                              ObjectMapper objectMapper,
                              CurrentUserService currentUserService) {
        this.savedSearchRepository = savedSearchRepository;
        this.propertyRepository = propertyRepository;
        this.objectMapper = objectMapper;
        this.currentUserService = currentUserService;
    }

    public SavedSearchDto saveSearch(SavedSearchDto searchDto) {
        User currentUser = getCurrentUser();

        SavedSearch savedSearch = new SavedSearch();
        savedSearch.setUser(currentUser);
        savedSearch.setName(searchDto.getName());
        savedSearch.setDescription(searchDto.getDescription());
        try {
            savedSearch.setFiltersJson(objectMapper.writeValueAsString(searchDto.getSearchCriteria()));
        } catch (Exception e) {
            throw new RuntimeException("Error serializando criterios de búsqueda", e);
        }

        SavedSearch saved = savedSearchRepository.save(savedSearch);

        return new SavedSearchDto(saved.getId(), saved.getName(), saved.getDescription(),
                searchDto.getSearchCriteria(), saved.getCreatedAt(), saved.getLastExecutedAt());
    }

    public List<SavedSearchDto> getSavedSearches() {
        User currentUser = getCurrentUser();
        return savedSearchRepository.findByUserId(currentUser.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void deleteSavedSearch(Long searchId) {
        User currentUser = getCurrentUser();
        SavedSearch search = savedSearchRepository.findById(searchId)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Búsqueda no encontrada"));
        savedSearchRepository.delete(search);
    }

    public List<Object> executeSavedSearch(Long searchId) {
        User currentUser = getCurrentUser();
        SavedSearch search = savedSearchRepository.findById(searchId)
                .filter(s -> s.getUser().getId().equals(currentUser.getId()))
                .orElseThrow(() -> new RuntimeException("Búsqueda no encontrada"));

        // Actualizar lastExecutedAt
        search.setLastExecutedAt(java.time.LocalDateTime.now());
        savedSearchRepository.save(search);

        // Aquí iría la lógica para ejecutar la búsqueda basada en los criterios
        // Por ahora, devolver todas las propiedades del usuario como ejemplo
        return propertyRepository.findByUser(currentUser).stream()
                .map(p -> Map.of(
                        "id", p.getId(),
                        "title", p.getTitle(),
                        "price", p.getPrice(),
                        "url", p.getSourceUrl()
                ))
                .collect(Collectors.toList());
    }

    private SavedSearchDto convertToDto(SavedSearch search) {
        try {
            Map<String, Object> criteria = objectMapper.readValue(search.getFiltersJson(),
                    new TypeReference<Map<String, Object>>() {});
            return new SavedSearchDto(search.getId(), search.getName(), search.getDescription(),
                    criteria, search.getCreatedAt(), search.getLastExecutedAt());
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando criterios de búsqueda", e);
        }
    }

    private User getCurrentUser() {
        return currentUserService.getCurrentUserOrDemo();
    }
}
