package com.tucasafacil.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "property_requirement")
public class PropertyRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private Boolean requiresPermanentContract = false;
    private Integer requiredPayslips;
    private Integer depositMonths;
    private Integer advanceMonths;
    private Boolean requiresGuarantor = false;
    private Boolean requiresRentDefaultInsurance = false;
    private BigDecimal estimatedMinimumIncome;
    private Boolean petsAllowed = true;
    private String preferredProfile;
    private Integer maxOccupants;
    private String notes;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public Boolean getRequiresPermanentContract() { return requiresPermanentContract; }
    public void setRequiresPermanentContract(Boolean requiresPermanentContract) { this.requiresPermanentContract = requiresPermanentContract; }

    public Integer getRequiredPayslips() { return requiredPayslips; }
    public void setRequiredPayslips(Integer requiredPayslips) { this.requiredPayslips = requiredPayslips; }

    public Integer getDepositMonths() { return depositMonths; }
    public void setDepositMonths(Integer depositMonths) { this.depositMonths = depositMonths; }

    public Integer getAdvanceMonths() { return advanceMonths; }
    public void setAdvanceMonths(Integer advanceMonths) { this.advanceMonths = advanceMonths; }

    public Boolean getRequiresGuarantor() { return requiresGuarantor; }
    public void setRequiresGuarantor(Boolean requiresGuarantor) { this.requiresGuarantor = requiresGuarantor; }

    public Boolean getRequiresRentDefaultInsurance() { return requiresRentDefaultInsurance; }
    public void setRequiresRentDefaultInsurance(Boolean requiresRentDefaultInsurance) { this.requiresRentDefaultInsurance = requiresRentDefaultInsurance; }

    public BigDecimal getEstimatedMinimumIncome() { return estimatedMinimumIncome; }
    public void setEstimatedMinimumIncome(BigDecimal estimatedMinimumIncome) { this.estimatedMinimumIncome = estimatedMinimumIncome; }

    public Boolean getPetsAllowed() { return petsAllowed; }
    public void setPetsAllowed(Boolean petsAllowed) { this.petsAllowed = petsAllowed; }

    public String getPreferredProfile() { return preferredProfile; }
    public void setPreferredProfile(String preferredProfile) { this.preferredProfile = preferredProfile; }

    public Integer getMaxOccupants() { return maxOccupants; }
    public void setMaxOccupants(Integer maxOccupants) { this.maxOccupants = maxOccupants; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}