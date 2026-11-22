package com.norgini.domain.model;

public record PlanetPosition(
		
        Planet planet, 
        ZodiacSign zodiacSign, 
        String degrees, 
        Integer house,
        boolean isRetrograde, 
        String moonPhase) {

    public PlanetPosition(Planet planet, ZodiacSign zodiacSign, String degrees) {
        this(planet, zodiacSign, degrees, null, false, null);
    }

    public static PlanetPosition createHouse(int number, double longitude) {
        int signIndex = (int) (longitude / 30) % 12;
        String degrees = String.format("%.2f°", longitude % 30);
        return new PlanetPosition(null, ZodiacSign.getByIndex(signIndex), degrees, null, false, "Casa " + number);
    }

    public static PlanetPosition createAscendant(double longitude) {
        int signIndex = (int) (longitude / 30) % 12;
        String degrees = String.format("%.2f°", longitude % 30);
        return new PlanetPosition(null, ZodiacSign.getByIndex(signIndex), degrees, null, false, "Ascendente");
    }
    
}
