package com.norgini.enums;

import swisseph.SweConst;

public enum FixedPlanet {

	SUN(SweConst.SE_SUN, "Sun"), 
	MOON(SweConst.SE_MOON, "Moon"), 
	MERCURY(SweConst.SE_MERCURY, "Mercury"),
	VENUS(SweConst.SE_VENUS, "Venus"), 
	MARS(SweConst.SE_MARS, "Mars"), 
	JUPITER(SweConst.SE_JUPITER, "Jupiter"),
	SATURN(SweConst.SE_SATURN, "Saturn");

	private final int id;
	private final String displayName;

	FixedPlanet(int id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}

	public int getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

}
