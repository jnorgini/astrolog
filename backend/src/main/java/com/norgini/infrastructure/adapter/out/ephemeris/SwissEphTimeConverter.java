package com.norgini.infrastructure.adapter.out.ephemeris;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import swisseph.SweDate;

public class SwissEphTimeConverter {

    public static double toJulianDay(int day, int month, int year, int hour, int minute) {
        ZonedDateTime localDateTime = ZonedDateTime.of(year, month, day, hour, minute, 0, 0,
                ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime utcDateTime = localDateTime.withZoneSameInstant(ZoneId.of("UTC"));

        double decimalHourUT = utcDateTime.getHour() + (utcDateTime.getMinute() / 60.0);
        return new SweDate(utcDateTime.getYear(), utcDateTime.getMonthValue(), utcDateTime.getDayOfMonth(),
                decimalHourUT).getJulDay();
    }
    
}
