package com.norgini.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.domain.port.in.CalculateBirthChartUseCase;
import com.norgini.domain.port.in.SearchLocationUseCase;
import com.norgini.infrastructure.adapter.in.web.dto.AstrologQueryParams;
import com.norgini.infrastructure.adapter.in.web.dto.PlanetPositionResponse;
import com.norgini.infrastructure.adapter.in.web.mapper.AstrologWebMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/astrolog")
@RequiredArgsConstructor
public class AstrologController {

	private final CalculateBirthChartUseCase calculateBirthChartUseCase;
	private final SearchLocationUseCase searchLocationUseCase;
	private final AstrologWebMapper webMapper;

	@GetMapping("/planets")
	public ResponseEntity<List<PlanetPositionResponse>> getAllPlanets(@Valid AstrologQueryParams params) {
		var positions = calculateBirthChartUseCase.calculatePositions(params.day(), params.month(), params.year(),
				params.hour(), params.minute(), params.location());

		List<PlanetPositionResponse> response = positions.stream().map(webMapper::toResponse).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/locations")
	public ResponseEntity<List<String>> getLocations(@RequestParam String query) {
		List<String> suggestions = searchLocationUseCase.getLocationsSuggestions(query);
		return ResponseEntity.ok(suggestions);
	}

	@GetMapping("/ping")
	public ResponseEntity<Void> ping() {
		return ResponseEntity.ok().build();
	}
	
}
