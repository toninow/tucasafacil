package com.tucasafacil.dto;

import java.util.ArrayList;
import java.util.List;

public class MobilityTargetDto {
    private String name;
    private String type;
    private Double latitude;
    private Double longitude;
    private List<TransportRouteDto> routes = new ArrayList<>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public List<TransportRouteDto> getRoutes() { return routes; }
    public void setRoutes(List<TransportRouteDto> routes) { this.routes = routes; }
}
