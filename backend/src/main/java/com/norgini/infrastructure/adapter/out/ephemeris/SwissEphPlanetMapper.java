package com.norgini.infrastructure.adapter.out.ephemeris;

import com.norgini.domain.model.Planet;
import swisseph.SweConst;

public class SwissEphPlanetMapper {

    public static int toSwissEphId(Planet planet) {
        return switch (planet) {
            case SOL -> SweConst.SE_SUN;
            case LUA -> SweConst.SE_MOON;
            case MERCURIO -> SweConst.SE_MERCURY;
            case VENUS -> SweConst.SE_VENUS;
            case MARTE -> SweConst.SE_MARS;
            case JUPITER -> SweConst.SE_JUPITER;
            case SATURNO -> SweConst.SE_SATURN;
            case URANO -> SweConst.SE_URANUS;
            case NETUNO -> SweConst.SE_NEPTUNE;
            case PLUTAO -> SweConst.SE_PLUTO;
            case NODO_VERDADEIRO -> SweConst.SE_TRUE_NODE;
            case CHIRON -> SweConst.SE_CHIRON;
            case LILITH -> SweConst.SE_MEAN_APOG;
        };
    }
    
}
