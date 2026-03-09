package com.tucasafacil.dto;

import java.math.BigDecimal;
import java.util.List;

public class ScoringResult {

    private BigDecimal scoreTotal;
    private BigDecimal housingScore;
    private BigDecimal areaScore;
    private BigDecimal requirementScore;
    private List<String> explanation;

    // Getters y Setters
    public BigDecimal getScoreTotal() { return scoreTotal; }
    public void setScoreTotal(BigDecimal scoreTotal) { this.scoreTotal = scoreTotal; }

    public BigDecimal getHousingScore() { return housingScore; }
    public void setHousingScore(BigDecimal housingScore) { this.housingScore = housingScore; }

    public BigDecimal getAreaScore() { return areaScore; }
    public void setAreaScore(BigDecimal areaScore) { this.areaScore = areaScore; }

    public BigDecimal getRequirementScore() { return requirementScore; }
    public void setRequirementScore(BigDecimal requirementScore) { this.requirementScore = requirementScore; }

    public List<String> getExplanation() { return explanation; }
    public void setExplanation(List<String> explanation) { this.explanation = explanation; }
}