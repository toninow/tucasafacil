package com.tucasafacil.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tucasafacil.dto.GeoPointDto;
import com.tucasafacil.dto.TransportRouteDto;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

@Service
public class GeoRoutingService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
    private static final String OSRM_URL = "https://router.project-osrm.org/route/v1";
    private static final String USER_AGENT = "TuCasaFacil/1.0 (dev@tucasafacil.local)";

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public GeoRoutingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public Optional<GeoPointDto> geocode(String query) {
        if (query == null || query.isBlank()) return Optional.empty();

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = NOMINATIM_URL + "?q=" + encodedQuery + "&format=json&limit=1";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .header("User-Agent", USER_AGENT)
                    .timeout(Duration.ofSeconds(12))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) return Optional.empty();

            JsonNode root = objectMapper.readTree(response.body());
            if (!root.isArray() || root.isEmpty()) return Optional.empty();

            JsonNode node = root.get(0);
            Double lat = parseDouble(node.path("lat").asText());
            Double lon = parseDouble(node.path("lon").asText());
            if (lat == null || lon == null) return Optional.empty();

            String displayName = node.path("display_name").asText(query);
            return Optional.of(new GeoPointDto(displayName, lat, lon));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<TransportRouteDto> route(String mode, GeoPointDto from, GeoPointDto to) {
        if (from == null || to == null || from.getLatitude() == null || from.getLongitude() == null
                || to.getLatitude() == null || to.getLongitude() == null) {
            return Optional.empty();
        }

        try {
            String url = OSRM_URL + "/" + mode + "/"
                    + from.getLongitude() + "," + from.getLatitude() + ";"
                    + to.getLongitude() + "," + to.getLatitude()
                    + "?overview=false&alternatives=false&steps=false";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .header("User-Agent", USER_AGENT)
                    .timeout(Duration.ofSeconds(12))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) return Optional.empty();

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode routes = root.path("routes");
            if (!routes.isArray() || routes.isEmpty()) return Optional.empty();

            JsonNode firstRoute = routes.get(0);
            double distanceMeters = firstRoute.path("distance").asDouble(-1);
            double durationSeconds = firstRoute.path("duration").asDouble(-1);
            if (distanceMeters < 0 || durationSeconds < 0) return Optional.empty();

            TransportRouteDto dto = new TransportRouteDto();
            dto.setMode(mode);
            dto.setDistanceKm(round(distanceMeters / 1000.0, 2));
            dto.setDurationMinutes((int) Math.round(durationSeconds / 60.0));
            return Optional.of(dto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return null;
        }
    }

    private static double round(double value, int scale) {
        double factor = Math.pow(10, scale);
        return Math.round(value * factor) / factor;
    }
}
