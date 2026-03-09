package com.tucasafacil.dto;

import java.math.BigDecimal;

public class PropertyRequirementDto {

    private Boolean requiresPermanentContract;
    private Integer requiredPayslips;
    private Integer depositMonths;
    private Integer advanceMonths;
    private Boolean requiresGuarantor;
    private Boolean requiresRentDefaultInsurance;
    private BigDecimal estimatedMinimumIncome;
    private Boolean petsAllowed;
    private String preferredProfile;
    private Integer maxOccupants;
    private String notes;

    // Getters y Setters
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