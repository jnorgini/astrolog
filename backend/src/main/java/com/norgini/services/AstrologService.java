package com.norgini.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.norgini.dtos.PlanetPositionResponse;
import com.norgini.dtos.GeocodeResult;
import com.norgini.engines.AstrologEngine;
import com.norgini.mappers.AstrologMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AstrologService {

	private final AstrologEngine astrologEngine;
	private final AstrologMapper astrologMapper;
	private final GeocodingService geocodingService;

	public List<PlanetPositionResponse> getAllPlanets(int day, int month, int year, int hour, int minute,
			String location) {
		GeocodeResult coords = geocodingService.findCoordinates(location);

		var calculatedPositions = astrologEngine.calculatePositions(day, month, year, hour, minute,
				coords.getLatitude(), coords.getLongitude());

		return calculatedPositions.stream().map(astrologMapper::toResponse).toList();
	}

	public List<String> getLocationsSuggestions(String query) {
		return geocodingService.searchLocations(query);
	}

}
