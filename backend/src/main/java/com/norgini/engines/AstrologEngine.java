package com.norgini.engines;

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

import com.norgini.dtos.PlanetCalculationResult;
import com.norgini.enums.FixedPlanet;
import com.norgini.enums.ZodiacSign;
import com.norgini.exceptions.InvalidAstrologDataException;

import lombok.RequiredArgsConstructor;
import swisseph.SweConst;
import swisseph.SwissEph;

@Component
@RequiredArgsConstructor
public class AstrologEngine {

	private final ResourceLoader resourceLoader;

	@Value("${swisseph.ephe.path:classpath:ephe/}")
	private String ephePath;

	public List<PlanetCalculationResult> calculatePositions(
			int day, 
			int month, 
			int year, 
			int hour, 
			int minute,
			double latitude, 
			double longitude) {
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
				targetTime = TimeConverter.targetTime(day, month, year, hour, minute);
			} catch (DateTimeException e) {
				throw new InvalidAstrologDataException(
						"Estouro de calendário: A data ou hora fornecida é inválida para o fuso 'America/Sao_Paulo'.",
						e);
			}

			StringBuffer errMsg = new StringBuffer();
			double[] cusps = new double[13];
			double[] ascmc = new double[10];
			sw.swe_houses(targetTime, 0, latitude, longitude, (int) 'P', cusps, ascmc);

			int flags = SweConst.SEFLG_SWIEPH | SweConst.SEFLG_SPEED;

			List<PlanetCalculationResult> finalResult;

			finalResult = new ArrayList<>(Stream.of(FixedPlanet.values()).map(planet -> {
				double[] xp = new double[6];

				if (sw.swe_calc_ut(targetTime, planet.getId(), flags, xp, errMsg) >= 0) {
					double fixedLongitude = xp[0] < 0 ? (xp[0] % 360) + 360 : xp[0];
					int signIndex = (int) (fixedLongitude / 30) % 12;

					String degrees = String.format("%.2f°", fixedLongitude % 30);
					int house = HouseDetector.findHouseForPlanet(xp[0], cusps);

					return new PlanetCalculationResult(planet, ZodiacSign.getByIndex(signIndex), degrees, house,
							xp[3] < 0, null);
				}
				return null;
			}).filter(Objects::nonNull).toList());
			IntStream.rangeClosed(1, 12).mapToObj(i -> {
				double fixedCusp = cusps[i] < 0 ? (cusps[i] % 360) + 360 : cusps[i];
				return AstrologFactory.createHouse(i, fixedCusp);
			}).forEach(finalResult::add);

			double fixedAsc = ascmc[0] < 0 ? (ascmc[0] % 360) + 360 : ascmc[0];
			finalResult.add(0, AstrologFactory.createAscendant(fixedAsc));

			return finalResult;
		} finally {
			sw.swe_close();
		}
	}
	
}
