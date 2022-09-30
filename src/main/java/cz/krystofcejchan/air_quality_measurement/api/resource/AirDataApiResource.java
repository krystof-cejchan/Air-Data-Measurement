package cz.krystofcejchan.air_quality_measurement.api.resource;

import cz.krystofcejchan.air_quality_measurement.api.service.AirDataApiService;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/airdata/api")
public class AirDataApiResource {
    private final AirDataApiService airDataApiService;


    @Contract(pure = true)
    public AirDataApiResource(AirDataApiService airDataApiService) {
        this.airDataApiService = airDataApiService;
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getAirDataById(@RequestHeader(required = false) String location) {
        return airDataApiService.getLatestAirData(location);
    }

    @GetMapping("/time/period")
    public ResponseEntity<?> getAirDataFromPeriodOfTime(@RequestHeader(required = true) String startDate,
                                                        @RequestHeader(required = true) String finishDate,
                                                        @RequestHeader(required = false) String startTime,
                                                        @RequestHeader(required = false) String finishTime) {
        LocalTime startT, finishT;
        LocalDate startD, finishD;
        try {
            startT = startTime == null ? LocalTime.MIN : LocalTime.parse(startTime);
            finishT = finishTime == null ? LocalTime.MAX : LocalTime.parse(finishTime);
            startD = LocalDate.parse(startDate);
            finishD = LocalDate.parse(finishDate);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(dateTimeParseException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return airDataApiService.getAverageAirDataFromDateToDate(LocalDateTime.of(startD, startT), LocalDateTime.of(finishD, finishT));
    }

    @GetMapping("/time/day")
    public ResponseEntity<?> getAirDataForSpecificDay(@RequestParam(required = true) String date) {
        LocalDate startD;
        try {
            startD = LocalDate.parse(date);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(dateTimeParseException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return airDataApiService.gerAirDataForOneSpecificDay(startD);
    }
}
