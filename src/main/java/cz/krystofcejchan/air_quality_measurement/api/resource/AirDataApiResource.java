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

    @GetMapping("/getByIdAndHash")
    public ResponseEntity<?> getAirDataByIdAndHash(@RequestParam() Long id,
                                                   @RequestParam() String hash) {
        return airDataApiService.getDataByIdAndHash(id, hash);
    }

    @GetMapping("/getLeaderboard")
    public ResponseEntity<LeaderboardData> getLeaderBoardStats() {
        Map<Map<Location, LeaderboardType>, List<AirData>> locationToMapOfLeaderboardTypeToAirData = new HashMap<>();

        for (Location location : Location.values()) {
            for (LeaderboardType leaderboardType : LeaderboardType.values()) {
                locationToMapOfLeaderboardTypeToAirData
                        .putIfAbsent(Map.of(location, leaderboardType),
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
