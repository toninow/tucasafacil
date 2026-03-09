package com.tucasafacil.dto;

import java.math.BigDecimal;

public class DashboardDto {

    private Long totalProperties;
    private BigDecimal averagePrice;
    private String topScoredPropertyTitle;
    private String lastAddedUrl;
    private String mostCommonDistrict;

    // Getters y Setters
    public Long getTotalProperties() { return totalProperties; }
    public void setTotalProperties(Long totalProperties) { this.totalProperties = totalProperties; }

    public BigDecimal getAveragePrice() { return averagePrice; }
    public void setAveragePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; }

    public String getTopScoredPropertyTitle() { return topScoredPropertyTitle; }
    public void setTopScoredPropertyTitle(String topScoredPropertyTitle) { this.topScoredPropertyTitle = topScoredPropertyTitle; }

    public String getLastAddedUrl() { return lastAddedUrl; }
    public void setLastAddedUrl(String lastAddedUrl) { this.lastAddedUrl = lastAddedUrl; }

    public String getMostCommonDistrict() { return mostCommonDistrict; }
    public void setMostCommonDistrict(String mostCommonDistrict) { this.mostCommonDistrict = mostCommonDistrict; }
}