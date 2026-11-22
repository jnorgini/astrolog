package com.norgini.application.service;

import java.util.List;

import com.norgini.domain.model.Coordinates;
import com.norgini.domain.model.PlanetPosition;
import com.norgini.domain.port.in.CalculateBirthChartUseCase;
import com.norgini.domain.port.in.SearchLocationUseCase;
import com.norgini.domain.port.out.AstrologyCalculatorPort;
import com.norgini.domain.port.out.GeocodingPort;

public class AstrologApplicationService implements CalculateBirthChartUseCase, SearchLocationUseCase {

	private final AstrologyCalculatorPort astrologyCalculatorPort;
	private final GeocodingPort geocodingPort;

	public AstrologApplicationService(AstrologyCalculatorPort astrologyCalculatorPort, GeocodingPort geocodingPort) {
		this.astrologyCalculatorPort = astrologyCalculatorPort;
		this.geocodingPort = geocodingPort;
	}

	@Override
	public List<PlanetPosition> calculatePositions(int day, int month, int year, int hour, int minute,
			String location) {
		Coordinates coords = geocodingPort.findCoordinates(location);
		return astrologyCalculatorPort.calculatePositions(day, month, year, hour, minute, coords);
	}

	@Override
	public List<String> getLocationsSuggestions(String query) {
		return geocodingPort.searchLocations(query);
	}
	
}
