package com.norgini.engines;

import java.util.stream.IntStream;

public class HouseDetector {

	public static int findHouseForPlanet(double planetLongitude, double[] cusps) {
		return IntStream.range(1, 12)
				.filter(h -> cusps[h] < cusps[h + 1] 
						? planetLongitude >= cusps[h] && planetLongitude < cusps[h + 1]
						: planetLongitude >= cusps[h] || planetLongitude < cusps[h + 1])
				.findFirst().orElse(12);
	}

}
