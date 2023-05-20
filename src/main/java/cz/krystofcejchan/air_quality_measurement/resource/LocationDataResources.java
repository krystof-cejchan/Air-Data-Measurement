package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/airdata/locations")
@CrossOrigin(origins = {"https://krystofcejchan.cz", "http://localhost:4200"}, methods = {RequestMethod.GET},
        maxAge = 60, allowedHeaders = "*", exposedHeaders = "*")
public record LocationDataResources(LocationDataRepository locationDataRepository) {
    @Contract(pure = true)
    public LocationDataResources {
    }

    @GetMapping("/get")
    public @NotNull ResponseEntity<LocationData> getLocationDataById(@RequestHeader Long id) {
        final Optional<LocationData> optionalLocationData = locationDataRepository.findById(id);
        return new ResponseEntity<>(optionalLocationData.orElse(null),
                optionalLocationData.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
