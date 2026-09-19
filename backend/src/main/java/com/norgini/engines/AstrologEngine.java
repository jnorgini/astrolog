package com.norgini.engines;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.norgini.dtos.PlanetCalculationResult;
import com.norgini.enums.FixedPlanet;
import com.norgini.enums.ZodiacSign;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

@Component
public class AstrologEngine {

	public List<PlanetCalculationResult> calculatePositions(int day, int month, int year) {
		SwissEph sw = new SwissEph();
		try {
			double julianDay = new SweDate(year, month, day, 12.0).getJulDay();
			StringBuffer errMsg = new StringBuffer();

			return Stream.of(FixedPlanet.values()).map(planet -> {
				double[] xp = new double[6];
				int ret = sw.swe_calc_ut(julianDay, planet.getId(), SweConst.SEFLG_SPEED, xp, errMsg);

				if (ret >= 0) {
					int signIndex = (int) (xp[0] / 30) % 12;
					String formattedDegrees = String.format("%.2f°", xp[0] % 30);
					return new PlanetCalculationResult(planet, ZodiacSign.getByIndex(signIndex), formattedDegrees);
				}
				return null;
			}).filter(Objects::nonNull).toList();
		} finally {
			sw.swe_close();
		}
	}

}
