package com.norgini.engines;

import org.springframework.stereotype.Component;
import com.norgini.enums.MoonPhase;
import swisseph.SweConst;
import swisseph.SwissEph;

@Component
public class MoonPhaseEngine {

	public MoonPhase calculateMoonPhase(SwissEph sw, double julianDay, int flags) {
		double[] resSun = new double[6];
		double[] resMoon = new double[6];
		StringBuffer err = new StringBuffer();

		sw.swe_calc_ut(julianDay, SweConst.SE_SUN, flags, resSun, err);
		double sunLong = resSun[0];
		sw.swe_calc_ut(julianDay, SweConst.SE_MOON, flags, resMoon, err);
		double moonLong = resMoon[0];
		double diffAngle = moonLong - sunLong;

		return MoonPhase.fromDegrees(diffAngle);
	}

}
