package com.tucasafacil.service;

import com.tucasafacil.dto.ScoringResult;
import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.UserPreference;
import com.tucasafacil.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoringService {

    private final UserPreferenceRepository userPreferenceRepository;

    public ScoringService(UserPreferenceRepository userPreferenceRepository) {
        this.userPreferenceRepository = userPreferenceRepository;
    }

    public ScoringResult calculateScore(Property property) {
        UserPreference prefs = userPreferenceRepository.findFirstByOrderByIdAsc();
        if (prefs == null) {
            prefs = new UserPreference(); // Valores por defecto
            prefs.setMaxBudget(BigDecimal.valueOf(1500));
            prefs.setAcceptsGuarantor(true);
            prefs.setAcceptsInsurance(true);
        }

        BigDecimal housingScore = calculateHousingScore(property, prefs);
        BigDecimal areaScore = calculateAreaScore(property, prefs);
        BigDecimal requirementScore = calculateRequirementScore(property, prefs);

        BigDecimal totalScore = housingScore.add(areaScore).add(requirementScore);

        property.setScore(totalScore);
        property.setHousingScore(housingScore);
        property.setAreaScore(areaScore);
        property.setRequirementScore(requirementScore);

        ScoringResult result = new ScoringResult();
        result.setScoreTotal(totalScore);
        result.setHousingScore(housingScore);
        result.setAreaScore(areaScore);
        result.setRequirementScore(requirementScore);
        result.setExplanation(buildExplanation(property, prefs));

        return result;
    }

    private BigDecimal calculateHousingScore(Property property, UserPreference prefs) {
        BigDecimal score = BigDecimal.ZERO;

        if (property.getPrice() != null && prefs.getMaxBudget() != null &&
            property.getPrice().compareTo(prefs.getMaxBudget()) <= 0) {
            score = score.add(BigDecimal.valueOf(25));
        }

        if (Boolean.TRUE.equals(property.getFurnished())) {
            score = score.add(BigDecimal.valueOf(10));
        }

        if (Boolean.TRUE.equals(property.getHasElevator())) {
            score = score.add(BigDecimal.valueOf(10));
        }

        return score;
    }

    private BigDecimal calculateAreaScore(Property property, UserPreference prefs) {
        BigDecimal score = BigDecimal.ZERO;

        if (property.getZoneAnalyses() != null && !property.getZoneAnalyses().isEmpty()) {
            ZoneAnalysis za = property.getZoneAnalyses().get(0);

            if (za.getNearestMetroDistanceMinutes() != null && za.getNearestMetroDistanceMinutes() <= 10) {
                score = score.add(BigDecimal.valueOf(15));
            }

            if (za.getSupermarketCount500m() != null && za.getSupermarketCount500m() > 0) {
                score = score.add(BigDecimal.valueOf(10));
            }

            if (prefs.getRequireGymNearby() && za.getGymCount1000m() != null && za.getGymCount1000m() > 0) {
                score = score.add(BigDecimal.valueOf(10));
            }
        }

        return score;
    }

    private BigDecimal calculateRequirementScore(Property property, UserPreference prefs) {
        BigDecimal score = BigDecimal.valueOf(20); // Base

        if (property.getRequirements() != null && !property.getRequirements().isEmpty()) {
            PropertyRequirement req = property.getRequirements().get(0);

            if (Boolean.TRUE.equals(req.getRequiresGuarantor()) && !prefs.getAcceptsGuarantor()) {
                score = score.subtract(BigDecimal.valueOf(20));
            }

            if (Boolean.TRUE.equals(req.getRequiresRentDefaultInsurance()) && !prefs.getAcceptsInsurance()) {
                score = score.subtract(BigDecimal.valueOf(15));
            }
        }

        return score.max(BigDecimal.ZERO);
    }

    private List<String> buildExplanation(Property property, UserPreference prefs) {
        List<String> explanation = new ArrayList<>();

        if (property.getPrice() != null && prefs.getMaxBudget() != null &&
            property.getPrice().compareTo(prefs.getMaxBudget()) <= 0) {
            explanation.add("Precio dentro del presupuesto");
        }

        if (property.getZoneAnalyses() != null && !property.getZoneAnalyses().isEmpty()) {
            ZoneAnalysis za = property.getZoneAnalyses().get(0);
            if (za.getNearestMetroDistanceMinutes() != null && za.getNearestMetroDistanceMinutes() <= 10) {
                explanation.add("Metro cercano");
            }
            if (za.getSupermarketCount500m() != null && za.getSupermarketCount500m() > 0) {
                explanation.add("Supermercados accesibles");
            }
        }

        if (property.getRequirements() != null && !property.getRequirements().isEmpty()) {
            PropertyRequirement req = property.getRequirements().get(0);
            if (Boolean.TRUE.equals(req.getRequiresRentDefaultInsurance())) {
                explanation.add("Se solicita seguro de impago");
            }
        }

        return explanation;
    }
}