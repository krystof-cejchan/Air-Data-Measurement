package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import cz.krystofcejchan.air_quality_measurement.service.AirDataLeaderboardService;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    LocationDataRepository locationDataRepository;

    @Contract(pure = true)
    public AirDataLeaderboardResource(AirDataLeaderboardService service) {
        this.service = service;
    }

    @GetMapping("/getAllByLocationAndLeaderboardType")
    public ResponseEntity<?> getAllByLocationAndLeaderboardType(@RequestParam() Long locationId,
                                                                @RequestParam() String leaderboardtype) throws DataNotFoundException {
        LeaderboardType leaderboardType1;
        try {
            leaderboardType1 = LeaderboardType.valueOf(leaderboardtype);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Optional<List<AirDataLeaderboard>> airDataLeaderboards = service
                .getAirDataLeaderboardByLocationAndLeaderboardType(locationDataRepository.findById(locationId)
                        .orElseThrow(DataNotFoundException::new), leaderboardType1);

        return airDataLeaderboards.orElse(Collections.emptyList()).isEmpty() ?
                new ResponseEntity<>(false, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(airDataLeaderboards.get(), HttpStatus.OK);
    }

    @GetMapping("/getAllDataFromLeaderboard")
    public ResponseEntity<?> getAll() {
        Optional<List<AirDataLeaderboard>> airDataLeaderboardList = service.getAllDataFromLeaderboard();
        return airDataLeaderboardList.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(airDataLeaderboardList.get(), HttpStatus.OK);
    }
}
