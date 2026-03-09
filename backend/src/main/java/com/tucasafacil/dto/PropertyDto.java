package com.tucasafacil.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PropertyDto {

    private Long id;
    private String sourcePortal;
    private String sourceUrl;
    private String title;
    private String description;
    private BigDecimal price;
    private String currency;
    private String address;
    private String district;
    private String neighborhood;
    private String city;
    private String province;
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer squareMeters;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer floor;
    private Boolean hasElevator;
    private Boolean furnished;
    private Boolean hasTerrace;
    private Boolean hasBalcony;
    private Boolean hasGarage;
    private Boolean hasStorageRoom;
    private Boolean expensesIncluded;
    private String listingType;
    private String advertiserType;
    private BigDecimal score;
    private BigDecimal housingScore;
    private BigDecimal areaScore;
    private BigDecimal requirementScore;
    private LocalDateTime extractedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PropertyRequirementDto> requirements;
    private List<ZoneAnalysisDto> zoneAnalyses;
    private List<PropertyImageDto> images;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSourcePortal() { return sourcePortal; }
    public void setSourcePortal(String sourcePortal) { this.sourcePortal = sourcePortal; }

    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public Integer getSquareMeters() { return squareMeters; }
    public void setSquareMeters(Integer squareMeters) { this.squareMeters = squareMeters; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }

    public Boolean getHasElevator() { return hasElevator; }
    public void setHasElevator(Boolean hasElevator) { this.hasElevator = hasElevator; }

    public Boolean getFurnished() { return furnished; }
    public void setFurnished(Boolean furnished) { this.furnished = furnished; }

    public Boolean getHasTerrace() { return hasTerrace; }
    public void setHasTerrace(Boolean hasTerrace) { this.hasTerrace = hasTerrace; }

    public Boolean getHasBalcony() { return hasBalcony; }
    public void setHasBalcony(Boolean hasBalcony) { this.hasBalcony = hasBalcony; }

    public Boolean getHasGarage() { return hasGarage; }
    public void setHasGarage(Boolean hasGarage) { this.hasGarage = hasGarage; }

    public Boolean getHasStorageRoom() { return hasStorageRoom; }
    public void setHasStorageRoom(Boolean hasStorageRoom) { this.hasStorageRoom = hasStorageRoom; }

    public Boolean getExpensesIncluded() { return expensesIncluded; }
    public void setExpensesIncluded(Boolean expensesIncluded) { this.expensesIncluded = expensesIncluded; }

    public String getListingType() { return listingType; }
    public void setListingType(String listingType) { this.listingType = listingType; }

    public String getAdvertiserType() { return advertiserType; }
    public void setAdvertiserType(String advertiserType) { this.advertiserType = advertiserType; }

    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }

    public BigDecimal getHousingScore() { return housingScore; }
    public void setHousingScore(BigDecimal housingScore) { this.housingScore = housingScore; }

    public BigDecimal getAreaScore() { return areaScore; }
    public void setAreaScore(BigDecimal areaScore) { this.areaScore = areaScore; }

    public BigDecimal getRequirementScore() { return requirementScore; }
    public void setRequirementScore(BigDecimal requirementScore) { this.requirementScore = requirementScore; }

    public LocalDateTime getExtractedAt() { return extractedAt; }
    public void setExtractedAt(LocalDateTime extractedAt) { this.extractedAt = extractedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<PropertyRequirementDto> getRequirements() { return requirements; }
    public void setRequirements(List<PropertyRequirementDto> requirements) { this.requirements = requirements; }

    public List<ZoneAnalysisDto> getZoneAnalyses() { return zoneAnalyses; }
    public void setZoneAnalyses(List<ZoneAnalysisDto> zoneAnalyses) { this.zoneAnalyses = zoneAnalyses; }

    public List<PropertyImageDto> getImages() { return images; }
    public void setImages(List<PropertyImageDto> images) { this.images = images; }
}