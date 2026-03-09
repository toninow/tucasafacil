package com.tucasafacil.dto;

import java.math.BigDecimal;

public class ZoneAnalysisDto {

    private Integer nearestMetroDistanceMinutes;
    private Integer nearestBusDistanceMinutes;
    private Integer supermarketCount500m;
    private Integer pharmacyCount500m;
    private Integer gymCount1000m;
    private Integer parkCount1000m;
    private Integer restaurantCount1000m;
    private Integer healthCenterCount2000m;
    private Integer shoppingCenterCount3000m;
    private BigDecimal entertainmentScore;
    private BigDecimal transportScore;
    private BigDecimal serviceScore;
    private String zoneSummary;

    // Getters y Setters
    public Integer getNearestMetroDistanceMinutes() { return nearestMetroDistanceMinutes; }
    public void setNearestMetroDistanceMinutes(Integer nearestMetroDistanceMinutes) { this.nearestMetroDistanceMinutes = nearestMetroDistanceMinutes; }

    public Integer getNearestBusDistanceMinutes() { return nearestBusDistanceMinutes; }
    public void setNearestBusDistanceMinutes(Integer nearestBusDistanceMinutes) { this.nearestBusDistanceMinutes = nearestBusDistanceMinutes; }

    public Integer getSupermarketCount500m() { return supermarketCount500m; }
    public void setSupermarketCount500m(Integer supermarketCount500m) { this.supermarketCount500m = supermarketCount500m; }

    public Integer getPharmacyCount500m() { return pharmacyCount500m; }
    public void setPharmacyCount500m(Integer pharmacyCount500m) { this.pharmacyCount500m = pharmacyCount500m; }

    public Integer getGymCount1000m() { return gymCount1000m; }
    public void setGymCount1000m(Integer gymCount1000m) { this.gymCount1000m = gymCount1000m; }

    public Integer getParkCount1000m() { return parkCount1000m; }
    public void setParkCount1000m(Integer parkCount1000m) { this.parkCount1000m = parkCount1000m; }

    public Integer getRestaurantCount1000m() { return restaurantCount1000m; }
    public void setRestaurantCount1000m(Integer restaurantCount1000m) { this.restaurantCount1000m = restaurantCount1000m; }

    public Integer getHealthCenterCount2000m() { return healthCenterCount2000m; }
    public void setHealthCenterCount2000m(Integer healthCenterCount2000m) { this.healthCenterCount2000m = healthCenterCount2000m; }

    public Integer getShoppingCenterCount3000m() { return shoppingCenterCount3000m; }
    public void setShoppingCenterCount3000m(Integer shoppingCenterCount3000m) { this.shoppingCenterCount3000m = shoppingCenterCount3000m; }

    public BigDecimal getEntertainmentScore() { return entertainmentScore; }
    public void setEntertainmentScore(BigDecimal entertainmentScore) { this.entertainmentScore = entertainmentScore; }

    public BigDecimal getTransportScore() { return transportScore; }
    public void setTransportScore(BigDecimal transportScore) { this.transportScore = transportScore; }

    public BigDecimal getServiceScore() { return serviceScore; }
    public void setServiceScore(BigDecimal serviceScore) { this.serviceScore = serviceScore; }

    public String getZoneSummary() { return zoneSummary; }
    public void setZoneSummary(String zoneSummary) { this.zoneSummary = zoneSummary; }
}