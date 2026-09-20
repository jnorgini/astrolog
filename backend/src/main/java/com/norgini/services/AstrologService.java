package com.norgini.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.norgini.dtos.PlanetPositionResponse;
import com.norgini.engines.AstrologEngine;
import com.norgini.mappers.AstrologMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AstrologService {

	private final AstrologEngine astrologEngine;
	private final AstrologMapper astrologMapper;

	public List<PlanetPositionResponse> getAllPlanets(int day, int month, int year, int hour, int minute,
			double latitude, double longitude) {
		var calculatedPositions = astrologEngine.calculatePositions(day, month, year, hour, minute, latitude,
				longitude);

		return calculatedPositions.stream().map(astrologMapper::toResponse).toList();
	}
}
