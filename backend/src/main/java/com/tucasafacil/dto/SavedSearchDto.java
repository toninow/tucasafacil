package com.tucasafacil.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class SavedSearchDto {
    private Long id;
    private String name;
    private String description;
    private Map<String, Object> searchCriteria;
    private LocalDateTime createdAt;
    private LocalDateTime lastExecutedAt;

    public SavedSearchDto() {}

    public SavedSearchDto(Long id, String name, String description, Map<String, Object> searchCriteria,
                         LocalDateTime createdAt, LocalDateTime lastExecutedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.searchCriteria = searchCriteria;
        this.createdAt = createdAt;
        this.lastExecutedAt = lastExecutedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Map<String, Object> getSearchCriteria() { return searchCriteria; }
    public void setSearchCriteria(Map<String, Object> searchCriteria) { this.searchCriteria = searchCriteria; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastExecutedAt() { return lastExecutedAt; }
    public void setLastExecutedAt(LocalDateTime lastExecutedAt) { this.lastExecutedAt = lastExecutedAt; }
}