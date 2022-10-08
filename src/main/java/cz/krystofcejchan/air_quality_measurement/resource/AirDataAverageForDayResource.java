package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.service.AirDataAverageOfDayService;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/airdata/avg")
public class AirDataAverageForDayResource {
    private final AirDataAverageOfDayService service;

    private HttpStatus resultCode = HttpStatus.OK;

    @Contract(pure = true)
    public AirDataAverageForDayResource(AirDataAverageOfDayService service) {
        this.service = service;
    }

    @GetMapping("/calc")
    public ResponseEntity<?> calcAverage() {

        Optional<HashMap<Location, AirDataAverageOfDay>> optionalAirDataAverageOfDay
                = service.getAverageAirDataForOneSpecificDay(LocalDate.now(ZoneId.of("Europe/Prague")));

        optionalAirDataAverageOfDay.ifPresentOrElse(
                (map) -> map.keySet().forEach(key -> service.addAirData(map.get(key))),
                () -> this.resultCode = HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(resultCode.getReasonPhrase(), resultCode);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirDataAverageOfDay>> getAllData() {
        return new ResponseEntity<>(service.getAllAvgAirData(), HttpStatus.OK);
    }
}