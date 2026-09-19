package com.norgini.enums;

public enum ZodiacSign {
	ARIES("Aries"), 
	TAURUS("Taurus"), 
	GEMINI("Gemini"), 
	CANCER("Cancer"), 
	LEO("Leo"), 
	VIRGO("Virgo"), 
	LIBRA("Libra"),
	SCORPIO("Scorpio"), 
	SAGITTARIUS("Sagittarius"), 
	CAPRICORN("Capricorn"), 
	AQUARIUS("Aquarius"), 
	PISCES("Pisces");

	private final String displayName;

	ZodiacSign(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	// Método utilitário para buscar o Signo diretamente pelo índice (0 a 11)
	// calculado pela biblioteca
	public static ZodiacSign getByIndex(int index) {
		return values()[index % 12];
	}

}
