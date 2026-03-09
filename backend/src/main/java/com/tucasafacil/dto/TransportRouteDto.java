package com.tucasafacil.dto;

public class TransportRouteDto {
    private String mode;
    private Double distanceKm;
    private Integer durationMinutes;

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
}
