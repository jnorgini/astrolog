package com.norgini.enums;

public enum ZodiacSign {
	ARIES("Áries"), 
	TAURUS("Touro"), 
	GEMINI("Gêmeos"), 
	CANCER("Câncer"), 
	LEO("Leão"), 
	VIRGO("Virgem"), 
	LIBRA("Libra"),
	SCORPIO("Escorpião"), 
	SAGITTARIUS("Sagitário"), 
	CAPRICORN("Capricórnio"), 
	AQUARIUS("Aquário"), 
	PISCES("Peixes");

	private final String displayName;

	ZodiacSign(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static ZodiacSign getByIndex(int index) {
		return values()[index % 12];
	}

}
