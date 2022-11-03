package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.service.AppInitializerService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airdata/APP_INITIALIZER/")
public record AppInitializerResource(
        AppInitializerService appInitializerService) {
    @Contract(pure = true)
    public AppInitializerResource {
    }

    @Contract(" -> new")
    @GetMapping("/location")
    public @NotNull
    ResponseEntity<Location[]> getAllLocations() {
        return new ResponseEntity<>(appInitializerService.getAllLocations(), HttpStatus.OK);
    }

    @Contract(" -> new")
    @GetMapping("/leaderboard")
    public @NotNull
    ResponseEntity<LeaderboardType[]> getAllLeaderboardTypes() {
        return new ResponseEntity<>(appInitializerService.getAllLeaderboardTypes(), HttpStatus.OK);
    }
}
