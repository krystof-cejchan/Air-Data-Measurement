package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.calc_avg.CalcAvgFactory;
import cz.krystofcejchan.air_quality_measurement.service.AirDataAverageOfDayService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * The type Air data average for day resource.
 */
@RestController
@RequestMapping("/airdata/avg")
@CrossOrigin(origins = {"https://krystofcejchan.cz", "http://localhost:4200"}, methods = {RequestMethod.GET, RequestMethod.PATCH},
        maxAge = 60, allowedHeaders = "*", exposedHeaders = "*")
public record AirDataAverageForDayResource(AirDataAverageOfDayService service) {
    /**
     * Instantiates a new Air data average for day resource.
     *
     * @param service the service
     */
    @Contract(pure = true)
    public AirDataAverageForDayResource {
    }


    @PatchMapping("/calc")
    public @NotNull
    ResponseEntity<?> calcAverage(@RequestHeader(required = false) String day) {
        LocalDate dayForAvgCalc;
        try {
            dayForAvgCalc = LocalDate.parse(day);
        } catch (DateTimeParseException e) {
            dayForAvgCalc = null;
        }
        return CalcAvgFactory.calc(service, dayForAvgCalc);
    }

    /**
     * Gets all data.
     *
     * @return the all data
     */
    @Contract(" -> new")
    @GetMapping("/all")
    public @NotNull
    ResponseEntity<List<AirDataAverageOfDay>> getAllData() {
        return new ResponseEntity<>(service.getAllAvgAirData(), HttpStatus.OK);
    }
}
