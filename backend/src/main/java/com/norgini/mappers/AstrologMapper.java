package com.norgini.mappers;

import org.springframework.stereotype.Component;
import com.norgini.dtos.PlanetCalculationResult;
import com.norgini.dtos.PlanetPositionResponse;

@Component
public class AstrologMapper {

	public PlanetPositionResponse toResponse(PlanetCalculationResult record) {
		if (record.planet() != null) {
			String movement = record.isRetrograde() ? "retrógrado" : "direto";
			return new PlanetPositionResponse(
					record.planet().getDisplayName(), 
					record.zodiacSign().getDisplayName(),
					record.degrees(), String.valueOf(record.house()), movement);
		}
		return new PlanetPositionResponse(record.customName(), record.zodiacSign().getDisplayName(), record.degrees(),
				"", "");
	}
	
}
