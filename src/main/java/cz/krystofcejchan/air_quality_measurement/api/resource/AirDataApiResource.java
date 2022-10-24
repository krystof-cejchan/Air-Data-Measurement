package cz.krystofcejchan.air_quality_measurement.api.resource;

import cz.krystofcejchan.air_quality_measurement.api.service.AirDataApiService;
import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.nondatabase_objects.LeaderboardData;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Air data api resource.
 */
@RestController
@RequestMapping("/airdata/api")
public class AirDataApiResource {
    private final AirDataApiService airDataApiService;


    /**
     * Instantiates a new Air data api resource.
     *
     * @param airDataApiService the air data api service
     */
    @Contract(pure = true)
    public AirDataApiResource(AirDataApiService airDataApiService) {
        this.airDataApiService = airDataApiService;
    }

    /**
     * Gets air data by id.
     *
     * @param location the location
     * @return the air data by id
     */
    @GetMapping("/latest")
    public ResponseEntity<?> getAirDataById(@RequestHeader(required = false) String location) {
        return airDataApiService.getLatestAirData(location);
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
    public ResponseEntity<?> getAirDataFromPeriodOfTime(@RequestHeader() String startDate,
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
        return airDataApiService.getAverageAirDataFromDateToDate(LocalDateTime.of(startD, startT), LocalDateTime.of(finishD, finishT));
    }


    /**
     * Gets average air data for specific day.
     *
     * @param date the date
     * @return the average air data for specific day
     */
    @GetMapping("/time/day_avg")
    public ResponseEntity<?> getAverageAirDataForSpecificDay(@RequestParam() String date) {
        LocalDate startD;
        try {
            startD = LocalDate.parse(date);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(dateTimeParseException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return airDataApiService.getAverageAirDataForOneSpecificDay(startD);
    }

    @GetMapping("/time/day")
    public ResponseEntity<?> getAirDataForSpecificDay(@RequestParam() String date) {
        LocalDate startD;
        try {
            startD = LocalDate.parse(date);
        } catch (DateTimeParseException dateTimeParseException) {
            return new ResponseEntity<>(dateTimeParseException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return airDataApiService.gerAirDataForOneSpecificDay(startD);
    }

    /**
     * Gets air data by id and hash.
     *
     * @param id   the id
     * @param hash the hash
     * @return the air data by id and hash
     */
    @GetMapping("/getByIdAndHash")
    public ResponseEntity<?> getAirDataByIdAndHash(@RequestParam() Long id,
                                                   @RequestParam() String hash) {
        return airDataApiService.getDataByIdAndHash(id, hash);
    }

    /**
     * Gets leader board stats.
     *
     * @return the leader board stats
     */
    @GetMapping("/getLeaderboard")
    public ResponseEntity<LeaderboardData> getLeaderBoardStats() {
        Map<String, List<AirData>> locationToMapOfLeaderboardTypeToAirData = new HashMap<>();

        for (Location location : Location.values()) {
            for (LeaderboardType leaderboardType : LeaderboardType.values()) {
                locationToMapOfLeaderboardTypeToAirData
                        .putIfAbsent(location.toString() + '=' + leaderboardType.toString(),
                                airDataApiService
                                        .getLeaderBoardData(leaderboardType,
                                                location)
                                        .orElse(Collections.singletonList(new AirData((byte) 0)))
                        );
            }
        }
        locationToMapOfLeaderboardTypeToAirData.values().removeIf(value -> value.stream()
                .allMatch(it -> it.getId() < 0L));

        return new ResponseEntity<>(new LeaderboardData(locationToMapOfLeaderboardTypeToAirData),
                locationToMapOfLeaderboardTypeToAirData.isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

}
