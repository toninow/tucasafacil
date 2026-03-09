package com.tucasafacil.service;

import com.tucasafacil.dto.DashboardDto;
import com.tucasafacil.entity.Property;
import com.tucasafacil.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DashboardService {

    private final PropertyRepository propertyRepository;

    public DashboardService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public DashboardDto getDashboard() {
        List<Property> properties = propertyRepository.findAll();

        DashboardDto dto = new DashboardDto();
        dto.setTotalProperties((long) properties.size());

        if (!properties.isEmpty()) {
            BigDecimal totalPrice = properties.stream()
                    .filter(p -> p.getPrice() != null)
                    .map(Property::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            dto.setAveragePrice(totalPrice.divide(BigDecimal.valueOf(properties.size()), 2, RoundingMode.HALF_UP));

            Property topScored = properties.stream()
                    .filter(p -> p.getScore() != null)
                    .max((p1, p2) -> p1.getScore().compareTo(p2.getScore()))
                    .orElse(null);
            if (topScored != null) {
                dto.setTopScoredPropertyTitle(topScored.getTitle());
            }

            Property lastAdded = properties.stream()
                    .max((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
                    .orElse(null);
            if (lastAdded != null) {
                dto.setLastAddedUrl(lastAdded.getSourceUrl());
            }

            // Distrito más común (mock simplificado)
            dto.setMostCommonDistrict("Centro");
        }

        return dto;
    }
}