package com.norgini.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.norgini.dtos.PlanetCalculationResult;
import com.norgini.enums.FixedPlanet;
import com.norgini.enums.ZodiacSign;

import lombok.RequiredArgsConstructor;
import swisseph.SweConst;
import swisseph.SwissEph;

@Component
@RequiredArgsConstructor
public class AstrologEngine {

	private final ResourceLoader resourceLoader;

	@Value("${swisseph.ephe.path:classpath:ephe/}")
	private String ephePath;

	public List<PlanetCalculationResult> calculatePositions(int day, int month, int year, int hour, int minute,
			double latitude, double longitude) {
		SwissEph sw = new SwissEph();
		try {
			try {
				String absolutePath = resourceLoader.getResource(ephePath).getFile().getAbsolutePath();
				sw.swe_set_ephe_path(absolutePath);
			} catch (IOException e) {
				System.err.println("Erro ao carregar pasta de efemérides: " + e.getMessage());
			}

			double targetTime = TimeConverter.targetTime(day, month, year, hour, minute);
			StringBuffer errMsg = new StringBuffer();
			double[] cusps = new double[13];
			double[] ascmc = new double[10];
			sw.swe_houses(targetTime, 0, latitude, longitude, (int) 'P', cusps, ascmc);

			int flags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_SPEED;

			List<PlanetCalculationResult> finalResult = new ArrayList<>(Stream.of(FixedPlanet.values()).map(planet -> {
				double[] xp = new double[6];

				if (sw.swe_calc_ut(targetTime, planet.getId(), flags, xp, errMsg) >= 0) {
					int signIndex = (int) (xp[0] / 30) % 12;
					String degrees = String.format("%.2f°", xp[0] % 30);
					int house = HouseDetector.findHouseForPlanet(xp[0], cusps);

					return new PlanetCalculationResult(planet, ZodiacSign.getByIndex(signIndex), degrees, house,
							xp[3] < 0, null);
				}
				return null;
			}).filter(Objects::nonNull).toList());

			IntStream.rangeClosed(1, 12).mapToObj(i -> AstrologFactory.createHouse(i, cusps[i]))
					.forEach(finalResult::add);

			finalResult.add(0, AstrologFactory.createAscendant(ascmc[0]));

			return finalResult;
		} finally {
			sw.swe_close();
		}
	}
}
