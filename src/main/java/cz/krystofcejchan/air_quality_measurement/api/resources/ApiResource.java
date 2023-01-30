package cz.krystofcejchan.air_quality_measurement.api.resources;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.service.AirDataService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airdata/api")
@CrossOrigin(origins = "*", methods = RequestMethod.GET,
        maxAge = 10, allowedHeaders = "*")
public final class ApiResource {
    private final AirDataService airDataService;

    @Contract(pure = true)
    public ApiResource(AirDataService airDataService) {
        this.airDataService = airDataService;
    }

    @GetMapping("/latest")
    public @NotNull ResponseEntity<List<AirData>> getLatestData(@RequestHeader(required = false) Long locationId) {
        return airDataService.getLatestAirData(locationId);
    }
}
