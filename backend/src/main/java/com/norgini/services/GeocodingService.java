package com.norgini.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.norgini.dtos.GeocodeResult;

@Service
public class GeocodingService {

	private final RestClient restClient;

	public GeocodingService() {
		this.restClient = RestClient.builder().baseUrl("https://nominatim.openstreetmap.org")
				.defaultHeader("User-Agent", "astrolog/1.0 (jnorgini@gmail.com)").build();
	}

	public GeocodeResult findCoordinates(String location) {
		try {
			GeocodeResult[] results = restClient
					.get().uri(uriBuilder -> uriBuilder.path("/search")
							.queryParam("q", location)
							.queryParam("format", "json")
							.queryParam("limit", "1")
							.build())
					.retrieve().body(GeocodeResult[].class);

			if (results != null && results.length > 0) {
				return results[0];
			}
		} catch (Exception e) {
			System.err.println("Erro ao buscar coordenadas para o local: " + location + ". Motivo: " + e.getMessage());
		}
		GeocodeResult fallback = new GeocodeResult();
		fallback.setLatitude(-23.5333);
		fallback.setLongitude(-46.6167);
		return fallback;
	}

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
