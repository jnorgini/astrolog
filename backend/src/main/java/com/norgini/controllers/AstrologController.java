package com.norgini.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.norgini.dtos.PlanetPositionResponse;
import com.norgini.services.AstrologService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/astrolog")
@RequiredArgsConstructor
public class AstrologController {

	private final AstrologService astrologyService;

	@GetMapping("/planets")
	public ResponseEntity<List<PlanetPositionResponse>> getAllPlanets(
			@RequestParam int day, 
			@RequestParam int month,
			@RequestParam int year) {

		List<PlanetPositionResponse> result = astrologyService.getAllPlanets(day, month, year);
		return ResponseEntity.ok(result);
	}
}
