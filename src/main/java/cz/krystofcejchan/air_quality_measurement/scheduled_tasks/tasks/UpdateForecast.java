package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks;

import cz.krystofcejchan.air_quality_measurement.forecast.ForecastDataList;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.DAY;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.TIME;
import cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.WeatherForecast;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateForecast implements ScheduledTaskRunnable {
    @Override
    public void runScheduledTask() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        Runnable updateForecast = () -> {
            WeatherForecast forecast = null;
            final String LOCATION = "Olomouc";
            try {
                forecast = new WeatherForecast(LOCATION, TIME.ALL, DAY.ALL);
            } catch (IOException e) {
                e.getCause().printStackTrace();
            }
            if (forecast != null && forecast.getAllSavedForecasts() != null && !forecast.getAllSavedForecasts().isEmpty()) {
                ForecastDataList.forecastAtHourList.clear();
                ForecastDataList.forecastAtHourList.addAll(forecast.getAllSavedForecasts());
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(updateForecast,
                5L,
                ForecastDataList.UPDATE_TIME.getSeconds(),
                TimeUnit.SECONDS);
    }
}
