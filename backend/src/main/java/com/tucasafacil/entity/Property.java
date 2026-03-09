package com.tucasafacil.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_portal_id")
    private SourcePortal sourcePortal;

    @Column(nullable = false, unique = true)
    private String sourceUrl;

    private String title;
    private String description;
    private BigDecimal price;
    private String currency = "EUR";
    private String address;
    private String district;
    private String neighborhood;
    private String city = "Madrid";
    private String province = "Madrid";
    private String postalCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer squareMeters;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer floor;
    private Boolean hasElevator = false;
    private Boolean furnished = false;
    private Boolean hasTerrace = false;
    private Boolean hasBalcony = false;
    private Boolean hasGarage = false;
    private Boolean hasStorageRoom = false;
    private Boolean expensesIncluded = false;
    private String listingType;
    private String advertiserType;
    private BigDecimal score;
    private BigDecimal housingScore;
    private BigDecimal areaScore;
    private BigDecimal requirementScore;
    private LocalDateTime extractedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyRequirement> requirements = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZoneAnalysis> zoneAnalyses = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyImage> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SourcePortal getSourcePortal() { return sourcePortal; }
    public void setSourcePortal(SourcePortal sourcePortal) { this.sourcePortal = sourcePortal; }

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

    public List<PropertyRequirement> getRequirements() {
        if (requirements == null) requirements = new ArrayList<>();
        return requirements;
    }
    public void setRequirements(List<PropertyRequirement> requirements) {
        this.requirements = requirements != null ? requirements : new ArrayList<>();
    }

    public List<ZoneAnalysis> getZoneAnalyses() {
        if (zoneAnalyses == null) zoneAnalyses = new ArrayList<>();
        return zoneAnalyses;
    }
    public void setZoneAnalyses(List<ZoneAnalysis> zoneAnalyses) {
        this.zoneAnalyses = zoneAnalyses != null ? zoneAnalyses : new ArrayList<>();
    }

    public List<PropertyImage> getImages() {
        if (images == null) images = new ArrayList<>();
        return images;
    }
    public void setImages(List<PropertyImage> images) {
        this.images = images != null ? images : new ArrayList<>();
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
