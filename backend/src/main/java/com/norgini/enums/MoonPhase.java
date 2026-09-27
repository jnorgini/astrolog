package com.norgini.enums;

public enum MoonPhase {
	NOVA("Nova"), 
	CRESCENTE("Crescente"), 
	CHEIA("Cheia"), 
	MINGUANTE("Minguante");

	private final String displayName;

	MoonPhase(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static MoonPhase fromDegrees(double degrees) {
		double normalized = (degrees % 360 + 360) % 360;

		if (normalized >= 337.5 || normalized < 22.5)
			return NOVA;
		if (normalized >= 22.5 && normalized < 112.5)
			return CRESCENTE;
		if (normalized >= 112.5 && normalized < 202.5)
			return CHEIA;
		if (normalized >= 202.5 && normalized < 292.5)
			return MINGUANTE;
		return NOVA;
	}

}
