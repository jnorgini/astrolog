package com.norgini.infrastructure.adapter.in.web.dto;

public record PlanetPositionResponse(
        String planet, 
        String zodiacSign, 
        String degrees, 
        String house, 
        String movement,
        String moonPhase) {
	
}
