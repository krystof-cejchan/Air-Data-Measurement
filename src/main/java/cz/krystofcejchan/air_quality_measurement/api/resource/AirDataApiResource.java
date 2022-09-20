package cz.krystofcejchan.air_quality_measurement.api.resource;

import cz.krystofcejchan.air_quality_measurement.api.service.AirDataApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/airdata/api")
public class AirDataApiResource {
    private final AirDataApiService airDataApiService;


    public AirDataApiResource(AirDataApiService airDataApiService) {
        this.airDataApiService = airDataApiService;
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getAirDataById(@RequestParam(required = false) String location) {
        return airDataApiService.getLatestAirData(location);
    }

    @GetMapping("/period")
    public ResponseEntity<?> getAirDataById(@RequestParam(required = true) String startDate,
                                            @RequestParam(required = true) String finishDate,
                                            @RequestParam(required = false) String startTime,
                                            @RequestParam(required = false) String finishTime) {
        LocalTime startT, finishT;
        LocalDate startD, finishD;
        try {
            startT = startTime == null ? LocalTime.MIN : LocalTime.parse(startTime);
            finishT = finishTime == null ? LocalTime.MAX : LocalTime.parse(finishTime);
            startD = LocalDate.parse(startDate);
            finishD = LocalDate.parse(finishDate);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(dateTimeParseException.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return airDataApiService
                .getAirDataFromDateToDate(LocalDateTime.of(startD, startT),
                        LocalDateTime.of(finishD, finishT));
    }
}