package cz.krystofcejchan.air_quality_measurement.forecast;

import cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.ForecastAtHour;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ForecastDataList {
    /**
     * static variable which contains forecast for the following 2 days along with the current day;
     * it is updated once every UPDATE_TIME
     */
    static public final List<ForecastAtHour> forecastAtHourList = new ArrayList<>();
    static public final Duration UPDATE_TIME = Duration.of(12, ChronoUnit.HOURS);
}
