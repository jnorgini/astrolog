package com.norgini.engines;

import com.norgini.dtos.PlanetCalculationResult;
import com.norgini.enums.ZodiacSign;

public class AstrologFactory {

    public static PlanetCalculationResult createHouse(int number, double longitude) {
        int signIndex = (int) (longitude / 30) % 12;
        String degrees = String.format("%.2f°", longitude % 30);
        return new PlanetCalculationResult(null, ZodiacSign.getByIndex(signIndex), degrees, null, false, "Casa " + number);
    }

    public static PlanetCalculationResult createAscendant(double longitude) {
        int signIndex = (int) (longitude / 30) % 12;
        String degrees = String.format("%.2f°", longitude % 30);
        return new PlanetCalculationResult(null, ZodiacSign.getByIndex(signIndex), degrees, null, false, "Ascendente");
    }
    
}
