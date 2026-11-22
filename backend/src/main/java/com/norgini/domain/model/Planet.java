package com.norgini.domain.model;

public enum Planet {
	
    SOL("Sol"),
    LUA("Lua"),
    MERCURIO("Mercúrio"),
    VENUS("Vênus"),
    MARTE("Marte"),
    JUPITER("Júpiter"),
    SATURNO("Saturno"),
    URANO("Urano"),
    NETUNO("Netuno"),
    PLUTAO("Plutão"),
    NODO_VERDADEIRO("Nódulo Verdadeiro"),
    CHIRON("Quíron"),
    LILITH("Lilith (Lua Negra)");

    private final String displayName;

    Planet(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
}
