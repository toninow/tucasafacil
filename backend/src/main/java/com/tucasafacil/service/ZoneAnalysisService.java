package com.tucasafacil.service;

import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.ZoneAnalysis;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ZoneAnalysisService {

    // Mock provider - reemplazar con API real (e.g., Google Places)
    public void analyze(Property property) {
        ZoneAnalysis za = new ZoneAnalysis();
        za.setProperty(property);

        // Mock data basado en ubicación
        za.setNearestMetroDistanceMinutes(5);
        za.setNearestBusDistanceMinutes(2);
        za.setSupermarketCount500m(3);
        za.setPharmacyCount500m(2);
        za.setGymCount1000m(1);
        za.setParkCount1000m(2);
        za.setRestaurantCount1000m(10);
        za.setHealthCenterCount2000m(1);
        za.setShoppingCenterCount3000m(1);
        za.setEntertainmentScore(BigDecimal.valueOf(7.5));
        za.setTransportScore(BigDecimal.valueOf(8.0));
        za.setServiceScore(BigDecimal.valueOf(6.0));
        za.setZoneSummary("Zona bien conectada con transporte público cercano y servicios básicos.");

        property.getZoneAnalyses().add(za);
    }
}