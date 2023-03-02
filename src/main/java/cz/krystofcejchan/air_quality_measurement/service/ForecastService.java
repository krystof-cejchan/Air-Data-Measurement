package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.forecast.ForecastDataList;
import cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.ForecastAtHour;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastService {
    @Autowired
    @Contract(pure = true)
    public ForecastService() {
    }

    public List<ForecastAtHour> getForecastFor3UpcomingDays() {
        return ForecastDataList.forecastAtHourList;
    }

}
