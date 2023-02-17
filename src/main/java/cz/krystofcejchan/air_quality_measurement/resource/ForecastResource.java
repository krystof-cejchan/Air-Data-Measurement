package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.service.ForecastService;
import cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.ForecastAtHour;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@link RestController} for forecast
 *
 * @param service {@link ForecastService}
 */
@RestController
@RequestMapping("/airdata/forecast")
@CrossOrigin(origins = {"https://krystofcejchan.cz", "http://localhost:4200"}, methods = {RequestMethod.GET},
        maxAge = 60, allowedHeaders = "*", exposedHeaders = "*")
public record ForecastResource(ForecastService service) {
    @Contract(pure = true)
    public ForecastResource {
    }

    /**
     * mapping to return all {@link cz.krystofcejchan.air_quality_measurement.forecast.ForecastMap} data
     *
     * @return response as {@link List} of {@link ForecastAtHour}
     */
    @GetMapping("/all")
    @Contract(pure = true)
    public @NotNull ResponseEntity<List<ForecastAtHour>> getForecastForAllDaysAndTimes() {
        List<ForecastAtHour> forecastData = service.getForecastFor3UpcomingDays();
        if (forecastData == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(forecastData, HttpStatus.OK);

    }
}
