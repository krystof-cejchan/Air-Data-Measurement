package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks;

import cz.krystofcejchan.air_quality_measurement.forecast.ForecastMap;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.DAY;
import cz.krystofcejchan.lite_weather_lib.enums_exception.enums.TIME;
import cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.WeatherForecast;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UpdateForecast implements ScheduledTaskRunnable {
    @Override
    public void runScheduledTask() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        Runnable updateForecast = () -> {
            synchronized (this) {
                WeatherForecast forecast = null;
                final String LOCATION = "Olomouc";
                AtomicInteger counter = new AtomicInteger(0);
                do {
                    try {
                        forecast = new WeatherForecast(LOCATION, TIME.ALL, DAY.ALL);
                    } catch (IOException e) {
                        e.getCause().printStackTrace();
                    }
                }
                while (forecast == null && counter.incrementAndGet() < 10);

                if (forecast != null && forecast.getAllSavedForecasts() != null && !forecast.getAllSavedForecasts().isEmpty()) {
                    ForecastMap.forecastMap.clear();
                    ForecastMap.forecastMap.addAll(forecast.getAllSavedForecasts());
                    //ForecastMap.forecastMap.replaceAll((k,v)->forecast.getAllSavedForecasts());
                }
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(updateForecast,
                0L,
                ForecastMap.UPDATE_TIME.getSeconds(),
                TimeUnit.SECONDS);
    }
}
