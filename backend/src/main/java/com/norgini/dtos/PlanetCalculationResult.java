package com.norgini.dtos;

import com.norgini.enums.FixedPlanet;
import com.norgini.enums.ZodiacSign;

public record PlanetCalculationResult(
		FixedPlanet planet, 
		ZodiacSign zodiacSign, 
		String degrees, 
		Integer house,
		boolean isRetrograde, 
		String customName) {
	public PlanetCalculationResult(FixedPlanet planet, ZodiacSign zodiacSign, String degrees) {
		this(planet, zodiacSign, degrees, null, false, null);
	}

}
