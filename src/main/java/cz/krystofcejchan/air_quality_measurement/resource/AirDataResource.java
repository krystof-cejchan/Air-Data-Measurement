package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.service.AirDataService;
import cz.krystofcejchan.air_quality_measurement.utilities.ZonedDateUtils;
import cz.krystofcejchan.air_quality_measurement.utilities.functional_interfaces.BooleanValidation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import static cz.krystofcejchan.air_quality_measurement.AqmApplication.ardtkn;

/**
 * The type Air data resource.
 */
@RestController
@RequestMapping("/airdata")
@CrossOrigin(origins = {"https://krystofcejchan.cz", "http://localhost:4200"}, methods = {RequestMethod.GET, RequestMethod.POST},
        maxAge = 60, allowedHeaders = "*", exposedHeaders = "*")
public record AirDataResource(AirDataService airDataService) {


    /**
     * Gets all air data.
     *
     * @return the all air data
     */
    @Contract(" -> new")
    @GetMapping("/all")
    public @NotNull ResponseEntity<List<AirData>> getAllAirData() {
        return new ResponseEntity<>(airDataService.findAllAirData(), HttpStatus.OK);
    }

    /**
     * Add air data response entity.
     *
     * @param airData the air data
     * @param token   the token
     * @return the response entity
     */
    @Contract("_, _, _ -> new")
    @PostMapping("/add")
    @CrossOrigin(origins = {"*"}, methods = {RequestMethod.POST},
            maxAge = 60, allowedHeaders = "*", exposedHeaders = "*")
    public @NotNull ResponseEntity<AirData> addAirData(@RequestBody(required = true) AirData airData,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                       @RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        if (airData == null || userAgent.isEmpty() || token.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Boolean> validations = new LinkedList<>();

        var tokenParams = token.split(";", 2);

        BooleanValidation<AirData, String[]> token_params1_validation = (airD, strArr) -> airD.getArduinoTime().equals(strArr[0]);
        BooleanValidation<String, String[]> token_params2_validation = (str1, strArr) -> str1.equals(strArr[1]);
        BooleanValidation<String, String> user_agent_validation = String::contains;

        validations.add(user_agent_validation.validPassed(Arrays.toString(userAgent.getBytes(StandardCharsets.UTF_8)),
                (Arrays.toString("ESP8266HTTPClient".getBytes(StandardCharsets.UTF_8)))));
        validations.add(token_params1_validation.validPassed(airData, tokenParams));
        validations.add(token_params2_validation.validPassed(ardtkn, tokenParams));


        if (validations.stream().allMatch(Boolean::booleanValue))
            return new ResponseEntity<>(airDataService.addAirData(airData,
                    LocalDateTime.now(ZonedDateUtils.getPragueZoneId())),
                    HttpStatus.CREATED);

        else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    }

    /**
     * Increase report number by one response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Contract("_ -> new")
    @PutMapping("/update_reportN")
    public @NotNull ResponseEntity<AirData> increaseReportNumberByOne(@RequestBody @NotNull Long id) {
        var reportingAirData = airDataService.updateNumberReportedById(id);
        return new ResponseEntity<>(reportingAirData, reportingAirData == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    /**
     * Gets air data by id.
     *
     * @param locationId the location
     * @return the air data by id
     */
    @GetMapping("/latest")
    public @NotNull ResponseEntity<?> getLatestData(@RequestHeader(required = false) Long locationId) {
        return airDataService.getLatestAirData(locationId);
    }

    @GetMapping("/average_temperature")
    public @NotNull ResponseEntity<? extends Number> getAverageTemperatureFromLatestData() {
        return airDataService.getCurrentAverageTemperature();
    }

    /**
     * Gets air data from period of time.
     *
     * @param startDate  the start date
     * @param finishDate the finish date
     * @param startTime  the start time
     * @param finishTime the finish time
     * @return the air data from period of time
     */
    @GetMapping("/time/period")
    public @NotNull ResponseEntity<?> getAirDataFromPeriodOfTime(@RequestHeader() String startDate,
                                                                 @RequestHeader() String finishDate,
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
        return airDataService.getAverageAirDataFromDateToDate(LocalDateTime.of(startD, startT), LocalDateTime.of(finishD, finishT));
    }

    /**
     * Gets average air data for specific day.
     *
     * @param date the date
     * @return the average air data for specific day
     */
    @GetMapping("/time/day_avg")
    public @NotNull ResponseEntity<?> getAverageAirDataForSpecificDay(@RequestHeader() String date) {
        LocalDate startD;
        try {
            startD = LocalDate.parse(date);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(dateTimeParseException.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }
        return airDataService.getAverageAirDataForOneSpecificDay(startD);
    }

    @GetMapping("/time/day")
    public @NotNull ResponseEntity<?> getAirDataForSpecificDay(@RequestHeader() String day) {
        LocalDate startD;
        try {
            startD = LocalDate.parse(day);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(dateTimeParseException.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }
        return airDataService.gerAirDataForOneSpecificDay(startD);
    }

    /**
     * gets all {@link AirData} from time range
     *
     * @param start date
     * @param end   date
     * @return an object extending {@link Collection} with unknown <?> generic type
     */
    @GetMapping("/time/day_range")
    public @NotNull ResponseEntity<? extends Collection<?>> getAirDataForDateRange(@RequestHeader() String start, @RequestHeader() String end) {
        LocalDate startD, endD;
        try {
            startD = LocalDate.parse(start);
            endD = LocalDate.parse(end);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(Collections.singleton(dateTimeParseException.getMessage()), HttpStatus.PRECONDITION_FAILED);
        }
        return airDataService.gerAirDataForDateRange(startD, endD);
    }

    /**
     * Gets air data by id and hash.
     *
     * @param id   the id
     * @param hash the hash
     * @return the air data by id and hash
     */
    @Contract("_, _ -> new")
    @GetMapping("/getByIdAndHash")
    public @NotNull ResponseEntity<?> getAirDataByIdAndHash(@RequestHeader() Long id, @RequestHeader() String hash) {
        return airDataService.getDataByIdAndHash(id, hash);
    }


}
