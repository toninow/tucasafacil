package com.tucasafacil.mapper;

import com.tucasafacil.dto.PropertyImageDto;
import com.tucasafacil.dto.PropertyDto;
import com.tucasafacil.dto.PropertyRequirementDto;
import com.tucasafacil.dto.ZoneAnalysisDto;
import com.tucasafacil.entity.Property;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PropertyMapper {

    public PropertyDto toDto(Property property) {
        if (property == null) return null;

        PropertyDto dto = new PropertyDto();
        dto.setId(property.getId());
        dto.setSourcePortal(property.getSourcePortal() != null ? property.getSourcePortal().getName() : null);
        dto.setSourceUrl(property.getSourceUrl());
        dto.setTitle(property.getTitle());
        dto.setDescription(property.getDescription());
        dto.setPrice(property.getPrice());
        dto.setCurrency(property.getCurrency());
        dto.setAddress(property.getAddress());
        dto.setDistrict(property.getDistrict());
        dto.setNeighborhood(property.getNeighborhood());
        dto.setCity(property.getCity());
        dto.setProvince(property.getProvince());
        dto.setPostalCode(property.getPostalCode());
        dto.setLatitude(property.getLatitude());
        dto.setLongitude(property.getLongitude());
        dto.setSquareMeters(property.getSquareMeters());
        dto.setBedrooms(property.getBedrooms());
        dto.setBathrooms(property.getBathrooms());
        dto.setFloor(property.getFloor());
        dto.setHasElevator(property.getHasElevator());
        dto.setFurnished(property.getFurnished());
        dto.setHasTerrace(property.getHasTerrace());
        dto.setHasBalcony(property.getHasBalcony());
        dto.setHasGarage(property.getHasGarage());
        dto.setHasStorageRoom(property.getHasStorageRoom());
        dto.setExpensesIncluded(property.getExpensesIncluded());
        dto.setListingType(property.getListingType());
        dto.setAdvertiserType(property.getAdvertiserType());
        dto.setScore(property.getScore());
        dto.setHousingScore(property.getHousingScore());
        dto.setAreaScore(property.getAreaScore());
        dto.setRequirementScore(property.getRequirementScore());
        dto.setExtractedAt(property.getExtractedAt());
        dto.setCreatedAt(property.getCreatedAt());
        dto.setUpdatedAt(property.getUpdatedAt());

        // Mapeo de listas (simplificado, puedes usar MapStruct para complejidad)
        if (property.getRequirements() != null) {
            dto.setRequirements(property.getRequirements().stream()
                    .map(req -> {
                        PropertyRequirementDto reqDto = new PropertyRequirementDto();
                        reqDto.setRequiresPermanentContract(req.getRequiresPermanentContract());
                        reqDto.setRequiredPayslips(req.getRequiredPayslips());
                        reqDto.setDepositMonths(req.getDepositMonths());
                        reqDto.setAdvanceMonths(req.getAdvanceMonths());
                        reqDto.setRequiresGuarantor(req.getRequiresGuarantor());
                        reqDto.setRequiresRentDefaultInsurance(req.getRequiresRentDefaultInsurance());
                        reqDto.setEstimatedMinimumIncome(req.getEstimatedMinimumIncome());
                        reqDto.setPetsAllowed(req.getPetsAllowed());
                        reqDto.setPreferredProfile(req.getPreferredProfile());
                        reqDto.setMaxOccupants(req.getMaxOccupants());
                        reqDto.setNotes(req.getNotes());
                        return reqDto;
                    }).collect(Collectors.toList()));
        }

        if (property.getZoneAnalyses() != null) {
            dto.setZoneAnalyses(property.getZoneAnalyses().stream()
                    .map(za -> {
                        ZoneAnalysisDto zaDto = new ZoneAnalysisDto();
                        zaDto.setNearestMetroDistanceMinutes(za.getNearestMetroDistanceMinutes());
                        zaDto.setNearestBusDistanceMinutes(za.getNearestBusDistanceMinutes());
                        zaDto.setSupermarketCount500m(za.getSupermarketCount500m());
                        zaDto.setPharmacyCount500m(za.getPharmacyCount500m());
                        zaDto.setGymCount1000m(za.getGymCount1000m());
                        zaDto.setParkCount1000m(za.getParkCount1000m());
                        zaDto.setRestaurantCount1000m(za.getRestaurantCount1000m());
                        zaDto.setHealthCenterCount2000m(za.getHealthCenterCount2000m());
                        zaDto.setShoppingCenterCount3000m(za.getShoppingCenterCount3000m());
                        zaDto.setEntertainmentScore(za.getEntertainmentScore());
                        zaDto.setTransportScore(za.getTransportScore());
                        zaDto.setServiceScore(za.getServiceScore());
                        zaDto.setZoneSummary(za.getZoneSummary());
                        return zaDto;
                    }).collect(Collectors.toList()));
        }

        if (property.getImages() != null) {
            dto.setImages(property.getImages().stream()
                    .map(img -> {
                        PropertyImageDto imgDto = new PropertyImageDto();
                        imgDto.setImageUrl(img.getImageUrl());
                        imgDto.setSortOrder(img.getSortOrder());
                        return imgDto;
                    }).collect(Collectors.toList()));
        }

        return dto;
    }
}
