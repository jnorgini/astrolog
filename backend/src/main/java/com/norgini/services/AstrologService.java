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

	private final AstrologEngine astrologyEngine;
	private final AstrologMapper astrologMapper;

	public List<PlanetPositionResponse> getAllPlanets(int day, int month, int year) {
		var calculatedPositions = astrologyEngine.calculatePositions(day, month, year);
		return calculatedPositions.stream().map(astrologMapper::toResponse).toList();
	}
	
}
