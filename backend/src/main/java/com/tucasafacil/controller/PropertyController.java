package com.tucasafacil.controller;

import com.tucasafacil.dto.AnalyzeUrlRequest;
import com.tucasafacil.dto.MobilityInsightsDto;
import com.tucasafacil.dto.MobilityRequest;
import com.tucasafacil.dto.PropertyDto;
import com.tucasafacil.service.PropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/analyze-url")
    public ResponseEntity<PropertyDto> analyzeUrl(@RequestBody AnalyzeUrlRequest request) {
        PropertyDto result = propertyService.analyzeUrl(request.getUrl());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        List<PropertyDto> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getProperty(@PathVariable Long id) {
        PropertyDto property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(property);
    }

    @PostMapping("/{id}/reanalyze")
    public ResponseEntity<PropertyDto> reanalyzeProperty(@PathVariable Long id) {
        PropertyDto result = propertyService.reanalyzeProperty(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/mobility")
    public ResponseEntity<MobilityInsightsDto> getMobilityInsights(
            @PathVariable Long id,
            @RequestBody(required = false) MobilityRequest request) {
        String destination = request != null ? request.getDestination() : null;
        MobilityInsightsDto result = propertyService.getMobilityInsights(id, destination);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
