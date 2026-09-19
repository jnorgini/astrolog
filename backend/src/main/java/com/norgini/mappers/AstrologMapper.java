package com.norgini.mappers;

import org.springframework.stereotype.Component;

import com.norgini.dtos.PlanetCalculationResult;
import com.norgini.dtos.PlanetPositionResponse;

@Component
public class AstrologMapper {

	public PlanetPositionResponse toResponse(PlanetCalculationResult record) {
		return new PlanetPositionResponse(
				record.planet().getDisplayName(), 
				record.zodiacSign().getDisplayName(),
				record.degrees());
	}
}
