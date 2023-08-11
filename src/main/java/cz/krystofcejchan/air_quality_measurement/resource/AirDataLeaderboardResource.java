package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import cz.krystofcejchan.air_quality_measurement.service.AirDataLeaderboardService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/airdata/leaderboard")
@CrossOrigin(origins = {"https://krystofcejchan.cz","http://localhost:4200"}, methods = {RequestMethod.GET},
        maxAge = 60, allowedHeaders = "*", exposedHeaders = "*")
public final class AirDataLeaderboardResource {
    private final AirDataLeaderboardService service;

    @Autowired
    LocationDataRepository locationDataRepository;

    @Contract(pure = true)
    public AirDataLeaderboardResource(AirDataLeaderboardService service) {
        this.service = service;
    }

    @GetMapping("/getAllDataFromLeaderboard")
    public @NotNull ResponseEntity<?> getAll() {
        Optional<List<AirDataLeaderboard>> airDataLeaderboardList = service.getAllDataFromLeaderboard();
        return airDataLeaderboardList.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(airDataLeaderboardList.get(), HttpStatus.OK);
    }
}
