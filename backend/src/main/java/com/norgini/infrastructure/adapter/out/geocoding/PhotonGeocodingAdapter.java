package com.norgini.infrastructure.adapter.out.geocoding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.norgini.domain.model.Coordinates;
import com.norgini.domain.port.out.GeocodingPort;

@Component
public class PhotonGeocodingAdapter implements GeocodingPort {

    private final RestClient restClient;

    public PhotonGeocodingAdapter() {
    	this.restClient = RestClient.builder()
    	        .baseUrl("https://photon.komoot.io")
    	        .defaultHeader("User-Agent", "astrolog/1.0 (jnorgini@gmail.com)")
    	        .build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Coordinates findCoordinates(String location) {
        try {
            Map<String, Object> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api")
                            .queryParam("q", location)
                            .queryParam("limit", "1")
                            .build())
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.containsKey("features")) {
                List<Map<String, Object>> features = (List<Map<String, Object>>) response.get("features");
                if (!features.isEmpty()) {
                    Map<String, Object> geometry = (Map<String, Object>) features.get(0).get("geometry");
                    List<Number> coords = (List<Number>) geometry.get("coordinates");
                    // No GeoJSON do Photon: [0] = longitude, [1] = latitude
                    double lon = coords.get(0).doubleValue();
                    double lat = coords.get(1).doubleValue();
                    return new Coordinates(lat, lon);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar coordenadas para o local: " + location + ". Motivo: " + e.getMessage());
        }
        return new Coordinates(-23.5333, -46.6167);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> searchLocations(String query) {
        try {
            Map<String, Object> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api")
                            .queryParam("q", query)
                            .queryParam("limit", "6")
                            .build())
                    .retrieve()
                    .body(Map.class);

            if (response == null || !response.containsKey("features")) {
                return Collections.emptyList();
            }

            List<Map<String, Object>> features = (List<Map<String, Object>>) response.get("features");
            List<String> suggestions = new ArrayList<>();

            for (Map<String, Object> feature : features) {
                Map<String, Object> props = (Map<String, Object>) feature.get("properties");
                if (props != null) {
                    String name = (String) props.get("name");
                    String city = (String) props.getOrDefault("city", name);
                    String state = props.containsKey("state") ? ", " + props.get("state") : "";
                    String country = props.containsKey("country") ? " - " + props.get("country") : "";

                    String formatted = (city != null ? city : name) + state + country;
                    if (!suggestions.contains(formatted)) {
                        suggestions.add(formatted);
                    }
                }
            }
            return suggestions;
        } catch (Exception e) {
            System.err.println("Erro ao autocompletar local: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
}
