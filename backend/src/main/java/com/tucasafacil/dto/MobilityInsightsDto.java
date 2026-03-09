package com.tucasafacil.dto;

import java.util.ArrayList;
import java.util.List;

public class MobilityInsightsDto {
    private GeoPointDto property;
    private List<MobilityTargetDto> targets = new ArrayList<>();

    public GeoPointDto getProperty() { return property; }
    public void setProperty(GeoPointDto property) { this.property = property; }

    public List<MobilityTargetDto> getTargets() { return targets; }
    public void setTargets(List<MobilityTargetDto> targets) { this.targets = targets; }
}
