package com.norgini.infrastructure.adapter.out.geocoding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.norgini.domain.model.Coordinates;
import com.norgini.domain.port.out.GeocodingPort;
import com.norgini.infrastructure.adapter.out.geocoding.dto.GeocodeResult;

@Component
public class NominatimGeocodingAdapter implements GeocodingPort {

    private final RestClient restClient;

    public NominatimGeocodingAdapter() {
        this.restClient = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader("User-Agent", "astrolog/1.0 (jnorgini@gmail.com)")
                .build();
    }

    @Override
    public Coordinates findCoordinates(String location) {
        try {
            GeocodeResult[] results = restClient
                    .get().uri(uriBuilder -> uriBuilder.path("/search")
                            .queryParam("q", location)
                            .queryParam("format", "json")
                            .queryParam("limit", "1")
                            .build())
                    .retrieve().body(GeocodeResult[].class);

            if (results != null && results.length > 0) {
                return new Coordinates(results[0].getLatitude(), results[0].getLongitude());
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
            Map<String, Object>[] results = restClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/search")
                            .queryParam("q", query)
                            .queryParam("format", "json")
                            .queryParam("addressdetails", "1")
                            .queryParam("accept-language", "pt")
                            .queryParam("limit", "6")
                            .build())
                    .retrieve().body(Map[].class);

            if (results == null || results.length == 0) {
                return Collections.emptyList();
            }

            List<String> suggestions = new ArrayList<>();
            for (Map<String, Object> item : results) {
                Map<String, Object> address = (Map<String, Object>) item.get("address");
                if (address != null) {
                    String city = (String) address.getOrDefault("city",
                            address.getOrDefault("town",
                            address.getOrDefault("village",
                            address.getOrDefault("municipality",
                            item.get("name")))));

                    String state = address.containsKey("state") ? ", " + address.get("state") : "";
                    String country = address.containsKey("country") ? " - " + address.get("country") : "";

                    suggestions.add(city + state + country);
                } else {
                    suggestions.add((String) item.get("display_name"));
                }
            }
            return suggestions;
        } catch (Exception e) {
            System.err.println("Erro ao autocompletar local: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    
}
