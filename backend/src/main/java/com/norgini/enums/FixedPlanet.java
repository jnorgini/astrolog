package com.norgini.enums;

import swisseph.SweConst;

public enum FixedPlanet {
	
	SOL(SweConst.SE_SUN, "Sol"),
    LUA(SweConst.SE_MOON, "Lua"),
    MERCURIO(SweConst.SE_MERCURY, "Mercúrio"),
    VENUS(SweConst.SE_VENUS, "Vênus"),
    MARTE(SweConst.SE_MARS, "Marte"),
    JUPITER(SweConst.SE_JUPITER, "Júpiter"),
    SATURNO(SweConst.SE_SATURN, "Saturno"),
    URANO(SweConst.SE_URANUS, "Urano"),
    NETUNO(SweConst.SE_NEPTUNE, "Netuno"),
    PLUTAO(SweConst.SE_PLUTO, "Plutão"),
    NODO_VERDADEIRO(SweConst.SE_TRUE_NODE, "Nódulo Verdadeiro"),
    CHIRON(SweConst.SE_CHIRON, "Quíron"),
    LILITH(SweConst.SE_MEAN_APOG, "Lilith (Lua Negra)"); 

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
