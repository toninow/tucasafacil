package com.tucasafacil.service;

import com.tucasafacil.dto.PropertyDto;
import com.tucasafacil.entity.Property;
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

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper,
                           List<PropertyExtractor> extractors, RequirementAnalyzer requirementAnalyzer,
                           ZoneAnalysisService zoneAnalysisService, ScoringService scoringService) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
        this.extractors = extractors;
        this.requirementAnalyzer = requirementAnalyzer;
        this.zoneAnalysisService = zoneAnalysisService;
        this.scoringService = scoringService;
    }

    public PropertyDto analyzeUrl(String url) {
        if (propertyRepository.existsBySourceUrl(url)) {
            throw new ExtractionException("URL ya analizada");
        }

        PropertyExtractor extractor = extractors.stream()
                .filter(e -> e.canExtract(url))
                .findFirst()
                .orElseThrow(() -> new ExtractionException("Extractor no encontrado"));

        try {
            Property property = extractor.extract(url);

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
        return propertyRepository.findAll().stream()
                .map(propertyMapper::toDto)
                .collect(Collectors.toList());
    }

    public PropertyDto getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Propiedad no encontrada"));
        return propertyMapper.toDto(property);
    }

    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new PropertyNotFoundException("Propiedad no encontrada");
        }
        propertyRepository.deleteById(id);
    }

    public PropertyDto reanalyzeProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyNotFoundException("Propiedad no encontrada"));

        // Re-ejecutar análisis
        requirementAnalyzer.analyze(property);
        zoneAnalysisService.analyze(property);
        scoringService.calculateScore(property);

        Property saved = propertyRepository.save(property);
        return propertyMapper.toDto(saved);
    }
}