package com.norgini.infrastructure.adapter.out.ephemeris;

import java.io.File;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.norgini.domain.exception.InvalidAstrologDataException;
import com.norgini.domain.model.Coordinates;
import com.norgini.domain.model.HouseCalculator;
import com.norgini.domain.model.MoonPhase;
import com.norgini.domain.model.Planet;
import com.norgini.domain.model.PlanetPosition;
import com.norgini.domain.model.ZodiacSign;
import com.norgini.domain.port.out.AstrologyCalculatorPort;

import lombok.RequiredArgsConstructor;
import swisseph.SweConst;
import swisseph.SwissEph;

@Component
@RequiredArgsConstructor
public class SwissEphAstrologyAdapter implements AstrologyCalculatorPort {

    private final ResourceLoader resourceLoader;

    @Value("${swisseph.ephe.path:classpath:ephe/}")
    private String ephePath;

    @Override
    public List<PlanetPosition> calculatePositions(
            int day, 
            int month, 
            int year, 
            int hour, 
            int minute,
            Coordinates coordinates) {
        
        SwissEph sw = new SwissEph();
        try {
            try {
                File dockerDir = new File("/app/ephe/");
                String path = (dockerDir.exists()) ? dockerDir.getAbsolutePath()
                        : resourceLoader.getResource(ephePath).getFile().getAbsolutePath();
                sw.swe_set_ephe_path(path);
            } catch (Exception e) {
                System.err.println("Aviso ao carregar efemérides: " + e.getMessage());
            }

            double targetTime;
            try {
                targetTime = SwissEphTimeConverter.toJulianDay(day, month, year, hour, minute);
            } catch (DateTimeException e) {
                throw new InvalidAstrologDataException(
                        "Estouro de calendário: A data ou hora fornecida é inválida para o fuso 'America/Sao_Paulo'.",
                        e);
            }

            StringBuffer errMsg = new StringBuffer();
            double[] cusps = new double[13];
            double[] ascmc = new double[10];
            sw.swe_houses(targetTime, 0, coordinates.latitude(), coordinates.longitude(), (int) 'P', cusps, ascmc);

            int flags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_SPEED;
            final double[] sunLongitudeHolder = new double[1];

            List<PlanetPosition> finalResult = new ArrayList<>(Stream.of(Planet.values()).map(planet -> {
                double[] xp = new double[6];
                int swissEphId = SwissEphPlanetMapper.toSwissEphId(planet);

                if (sw.swe_calc_ut(targetTime, swissEphId, flags, xp, errMsg) >= 0) {
                    double fixedLongitude = xp[0] < 0 ? (xp[0] % 360) + 360 : xp[0];
                    int signIndex = (int) (fixedLongitude / 30) % 12;

                    String degrees = String.format("%.2f°", fixedLongitude % 30);
                    int house = HouseCalculator.findHouseForPlanet(xp[0], cusps);
                    
                    String customNameValue = null;

                    if (planet == Planet.SOL) {
                        sunLongitudeHolder[0] = fixedLongitude;
                    } else if (planet == Planet.LUA) {
                        if (sunLongitudeHolder[0] == 0) {
                            double[] sunXp = new double[6];
                            int sunId = SwissEphPlanetMapper.toSwissEphId(Planet.SOL);
                            if (sw.swe_calc_ut(targetTime, sunId, flags, sunXp, errMsg) >= 0) {
                                sunLongitudeHolder[0] = sunXp[0] < 0 ? (sunXp[0] % 360) + 360 : sunXp[0];
                            }
                        }
                        double diffAngle = fixedLongitude - sunLongitudeHolder[0];
                        MoonPhase phase = MoonPhase.fromDegrees(diffAngle);
                        customNameValue = phase.getDisplayName();
                    }
                    return new PlanetPosition(planet, ZodiacSign.getByIndex(signIndex), degrees, house,
                            xp[3] < 0, customNameValue);
                }
                return null;
            }).filter(Objects::nonNull).toList());

            IntStream.rangeClosed(1, 12).mapToObj(i -> {
                double fixedCusp = cusps[i] < 0 ? (cusps[i] % 360) + 360 : cusps[i];
                return PlanetPosition.createHouse(i, fixedCusp);
            }).forEach(finalResult::add);

            double fixedAsc = ascmc[0] < 0 ? (ascmc[0] % 360) + 360 : ascmc[0];
            finalResult.add(0, PlanetPosition.createAscendant(fixedAsc));

            return finalResult;
        } finally {
            sw.swe_close();
        }
    }
    
}
