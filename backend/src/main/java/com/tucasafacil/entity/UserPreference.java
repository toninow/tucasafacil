package com.tucasafacil.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_preference")
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal maxBudget;
    private String preferredZones; // JSON
    private Integer minBedrooms;
    private Integer maxDepositMonths;
    private Boolean preferPrivateOwner = false;
    private Boolean requireGoodTransport = false;
    private Boolean requireSupermarketNearby = false;
    private Boolean requireGymNearby = false;
    private Boolean valueEntertainment = false;
    private Boolean acceptsGuarantor = true;
    private Boolean acceptsInsurance = true;
    private BigDecimal referenceLatitude;
    private BigDecimal referenceLongitude;
    private String scoringWeightsJson;
    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getMaxBudget() { return maxBudget; }
    public void setMaxBudget(BigDecimal maxBudget) { this.maxBudget = maxBudget; }

    public String getPreferredZones() { return preferredZones; }
    public void setPreferredZones(String preferredZones) { this.preferredZones = preferredZones; }

    public Integer getMinBedrooms() { return minBedrooms; }
    public void setMinBedrooms(Integer minBedrooms) { this.minBedrooms = minBedrooms; }

    public Integer getMaxDepositMonths() { return maxDepositMonths; }
    public void setMaxDepositMonths(Integer maxDepositMonths) { this.maxDepositMonths = maxDepositMonths; }

    public Boolean getPreferPrivateOwner() { return preferPrivateOwner; }
    public void setPreferPrivateOwner(Boolean preferPrivateOwner) { this.preferPrivateOwner = preferPrivateOwner; }

    public Boolean getRequireGoodTransport() { return requireGoodTransport; }
    public void setRequireGoodTransport(Boolean requireGoodTransport) { this.requireGoodTransport = requireGoodTransport; }

    public Boolean getRequireSupermarketNearby() { return requireSupermarketNearby; }
    public void setRequireSupermarketNearby(Boolean requireSupermarketNearby) { this.requireSupermarketNearby = requireSupermarketNearby; }

    public Boolean getRequireGymNearby() { return requireGymNearby; }
    public void setRequireGymNearby(Boolean requireGymNearby) { this.requireGymNearby = requireGymNearby; }

    public Boolean getValueEntertainment() { return valueEntertainment; }
    public void setValueEntertainment(Boolean valueEntertainment) { this.valueEntertainment = valueEntertainment; }

    public Boolean getAcceptsGuarantor() { return acceptsGuarantor; }
    public void setAcceptsGuarantor(Boolean acceptsGuarantor) { this.acceptsGuarantor = acceptsGuarantor; }

    public Boolean getAcceptsInsurance() { return acceptsInsurance; }
    public void setAcceptsInsurance(Boolean acceptsInsurance) { this.acceptsInsurance = acceptsInsurance; }

    public BigDecimal getReferenceLatitude() { return referenceLatitude; }
    public void setReferenceLatitude(BigDecimal referenceLatitude) { this.referenceLatitude = referenceLatitude; }

    public BigDecimal getReferenceLongitude() { return referenceLongitude; }
    public void setReferenceLongitude(BigDecimal referenceLongitude) { this.referenceLongitude = referenceLongitude; }

    public String getScoringWeightsJson() { return scoringWeightsJson; }
    public void setScoringWeightsJson(String scoringWeightsJson) { this.scoringWeightsJson = scoringWeightsJson; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
