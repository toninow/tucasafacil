package com.tucasafacil.dto;

import java.time.LocalDateTime;

public class FavoritePropertyDto {
    private Long id;
    private Long propertyId;
    private String propertyTitle;
    private String propertyUrl;
    private LocalDateTime addedAt;

    public FavoritePropertyDto() {}

    public FavoritePropertyDto(Long id, Long propertyId, String propertyTitle, String propertyUrl, LocalDateTime addedAt) {
        this.id = id;
        this.propertyId = propertyId;
        this.propertyTitle = propertyTitle;
        this.propertyUrl = propertyUrl;
        this.addedAt = addedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public String getPropertyTitle() { return propertyTitle; }
    public void setPropertyTitle(String propertyTitle) { this.propertyTitle = propertyTitle; }

    public String getPropertyUrl() { return propertyUrl; }
    public void setPropertyUrl(String propertyUrl) { this.propertyUrl = propertyUrl; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}