package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.service.AirDataLeaderboardService;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/airdata/leaderboard")
public class AirDataLeaderboardResource {
    private final AirDataLeaderboardService service;

    @Contract(pure = true)
    public AirDataLeaderboardResource(AirDataLeaderboardService service) {
        this.service = service;
    }

    @GetMapping("/getAllByLocationAndLeaderboardType")
    public ResponseEntity<?> getAllByLocationAndLeaderboardType(@RequestParam() String location,
                                                                @RequestParam() String leaderboardtype) {
        Location location1;
        LeaderboardType leaderboardType1;
        try {
            location1 = Location.valueOf(location);
            leaderboardType1 = LeaderboardType.valueOf(leaderboardtype);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Optional<List<AirDataLeaderboard>> airDataLeaderboards = service
                .getAirDataLeaderboardByLocationAndLeaderboardType(location1, leaderboardType1);

        return airDataLeaderboards.orElse(Collections.emptyList()).isEmpty() ?
                new ResponseEntity<>(false, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(airDataLeaderboards.get(), HttpStatus.OK);
    }
}
