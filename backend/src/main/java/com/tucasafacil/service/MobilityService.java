package com.tucasafacil.service;

import com.tucasafacil.dto.GeoPointDto;
import com.tucasafacil.dto.MobilityInsightsDto;
import com.tucasafacil.dto.MobilityTargetDto;
import com.tucasafacil.dto.TransportRouteDto;
import com.tucasafacil.entity.Property;
import com.tucasafacil.exception.ExtractionException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MobilityService {

    private static final String STATION_CARPETANA = "Metro Carpetana, Madrid";
    private static final String STATION_CARABANCHEL = "Metro Carabanchel, Madrid";
    private static final List<String> TRANSPORT_MODES = Arrays.asList("walking", "cycling", "driving");

    private final GeoRoutingService geoRoutingService;

    public MobilityService(GeoRoutingService geoRoutingService) {
        this.geoRoutingService = geoRoutingService;
    }

    public MobilityInsightsDto buildInsights(Property property, String customDestination) {
        GeoPointDto propertyPoint = resolvePropertyPoint(property)
                .orElseThrow(() -> new ExtractionException("No se pudo resolver la ubicacion real del inmueble"));

        MobilityInsightsDto response = new MobilityInsightsDto();
        response.setProperty(propertyPoint);

        List<MobilityTargetDto> targets = new ArrayList<>();
        addStationTarget(targets, "Metro Carpetana", STATION_CARPETANA, propertyPoint);
        addStationTarget(targets, "Metro Carabanchel", STATION_CARABANCHEL, propertyPoint);

        if (customDestination != null && !customDestination.isBlank()) {
            geoRoutingService.geocode(customDestination.trim()).ifPresent(destinationPoint -> {
                targets.add(buildTarget("Destino personalizado", "custom", destinationPoint, propertyPoint));
            });
        }

        response.setTargets(targets);
        return response;
    }

    private void addStationTarget(List<MobilityTargetDto> targets, String label, String query, GeoPointDto propertyPoint) {
        geoRoutingService.geocode(query).ifPresent(stationPoint -> {
            targets.add(buildTarget(label, "station", stationPoint, propertyPoint));
        });
    }

    private MobilityTargetDto buildTarget(String label, String type, GeoPointDto targetPoint, GeoPointDto propertyPoint) {
        MobilityTargetDto target = new MobilityTargetDto();
        target.setName(label);
        target.setType(type);
        target.setLatitude(targetPoint.getLatitude());
        target.setLongitude(targetPoint.getLongitude());

        List<TransportRouteDto> routes = TRANSPORT_MODES.stream()
                .map(mode -> geoRoutingService.route(mode, propertyPoint, targetPoint))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        target.setRoutes(routes);
        return target;
    }

    private Optional<GeoPointDto> resolvePropertyPoint(Property property) {
        if (property.getLatitude() != null && property.getLongitude() != null) {
            return Optional.of(new GeoPointDto(
                    property.getAddress(),
                    property.getLatitude().doubleValue(),
                    property.getLongitude().doubleValue()
            ));
        }

        String query = buildBestQuery(property);
        Optional<GeoPointDto> geocoded = geoRoutingService.geocode(query);
        geocoded.ifPresent(point -> {
            property.setLatitude(BigDecimal.valueOf(point.getLatitude()));
            property.setLongitude(BigDecimal.valueOf(point.getLongitude()));
        });
        return geocoded;
    }

    private String buildBestQuery(Property property) {
        StringBuilder query = new StringBuilder();

        appendPart(query, property.getAddress());
        appendPart(query, property.getNeighborhood());
        appendPart(query, property.getDistrict());
        appendPart(query, property.getCity());
        appendPart(query, property.getProvince());
        appendPart(query, property.getPostalCode());

        if (query.isEmpty()) {
            appendPart(query, property.getTitle());
        }
        if (query.isEmpty()) {
            appendPart(query, "Madrid");
        }
        appendPart(query, "Espana");

        return query.toString();
    }

    private void appendPart(StringBuilder query, String value) {
        if (value == null || value.isBlank()) return;
        if (!query.isEmpty()) query.append(", ");
        query.append(value.trim());
    }
}
