package com.tucasafacil.service;

import com.tucasafacil.dto.PropertyDto;
import com.tucasafacil.dto.MobilityInsightsDto;
import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.User;
import com.tucasafacil.exception.ExtractionException;
import com.tucasafacil.exception.PropertyNotFoundException;
import com.tucasafacil.mapper.PropertyMapper;
import com.tucasafacil.repository.PropertyRepository;
import com.tucasafacil.scraper.PropertyExtractor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final List<PropertyExtractor> extractors;
    private final RequirementAnalyzer requirementAnalyzer;
    private final ZoneAnalysisService zoneAnalysisService;
    private final ScoringService scoringService;
    private final CurrentUserService currentUserService;
    private final MobilityService mobilityService;

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper,
                           List<PropertyExtractor> extractors, RequirementAnalyzer requirementAnalyzer,
                           ZoneAnalysisService zoneAnalysisService, ScoringService scoringService,
                           CurrentUserService currentUserService, MobilityService mobilityService) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
        this.extractors = extractors;
        this.requirementAnalyzer = requirementAnalyzer;
        this.zoneAnalysisService = zoneAnalysisService;
        this.scoringService = scoringService;
        this.currentUserService = currentUserService;
        this.mobilityService = mobilityService;
    }

    private User getCurrentUser() {
        return currentUserService.getCurrentUserOrDemo();
    }

    public PropertyDto analyzeUrl(String url) {
        User user = getCurrentUser();

        var existing = propertyRepository.findBySourceUrl(url);
        if (existing.isPresent()) {
            Property property = existing.get();
            if (property.getUser() != null && property.getUser().getId().equals(user.getId())) {
                return propertyMapper.toDto(property);
            }
            throw new ExtractionException("URL ya analizada por otro usuario");
        }

        PropertyExtractor extractor = extractors.stream()
                .filter(e -> e.canExtract(url))
                .findFirst()
                .orElseThrow(() -> new ExtractionException("Extractor no encontrado"));

        try {
            Property property = extractor.extract(url);
            property.setUser(user); // Asociar al usuario

            // Análisis de requisitos
            requirementAnalyzer.analyze(property);

            // Análisis de zona
            zoneAnalysisService.analyze(property);

            // Calcular score
            scoringService.calculateScore(property);

            Property saved = propertyRepository.save(property);
            return propertyMapper.toDto(saved);
        } catch (Exception e) {
            throw new ExtractionException("Error extrayendo datos: " + e.getMessage(), e);
        }
    }

    public List<PropertyDto> getAllProperties() {
        User user = getCurrentUser();
        return propertyRepository.findByUserId(user.getId()).stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }

    public PropertyDto getPropertyById(Long id) {
        Property property = getPropertyForCurrentUser(id);
        return propertyMapper.toDto(property);
    }

    public void deleteProperty(Long id) {
        Property property = getPropertyForCurrentUser(id);
        propertyRepository.delete(property);
    }

    public PropertyDto reanalyzeProperty(Long id) {
        Property property = getPropertyForCurrentUser(id);

        // Re-ejecutar análisis
        requirementAnalyzer.analyze(property);
        zoneAnalysisService.analyze(property);
        scoringService.calculateScore(property);

        Property saved = propertyRepository.save(property);
        return propertyMapper.toDto(saved);
    }

    public MobilityInsightsDto getMobilityInsights(Long id, String destination) {
        Property property = getPropertyForCurrentUser(id);
        MobilityInsightsDto insights = mobilityService.buildInsights(property, destination);
        propertyRepository.save(property);
        return insights;
    }

    private Property getPropertyForCurrentUser(Long id) {
        User user = getCurrentUser();
        return propertyRepository.findById(id)
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new PropertyNotFoundException("Propiedad no encontrada"));
    }
}
