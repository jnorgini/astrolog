package com.norgini.dtos;

import com.norgini.enums.FixedPlanet;
import com.norgini.enums.ZodiacSign;

public record PlanetCalculationResult(FixedPlanet planet, ZodiacSign zodiacSign, String degrees) {
}
