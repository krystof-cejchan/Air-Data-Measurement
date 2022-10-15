package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.service.AirDataService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airdata")
public record AirDataResource(AirDataService airDataService) {
    @Contract(pure = true)
    public AirDataResource {
    }

    @Contract(" -> new")
    @GetMapping("/all")
    public @NotNull ResponseEntity<List<AirData>> getAllAirData() {
        return new ResponseEntity<>(airDataService.findAllAirData(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public @NotNull ResponseEntity<?> getAirDataById(@PathVariable("id") Long id) {
        if (airDataService.existAirData(id))
            return new ResponseEntity<>(airDataService.findAirData(id), HttpStatus.OK);
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Contract("_ -> new")
    @GetMapping("/exists/{id}")
    public @NotNull ResponseEntity<Boolean> existAirData(@PathVariable("id") Long id) {
        return new ResponseEntity<>(airDataService.existAirData(id), HttpStatus.OK);
    }

    @Contract("_, _ -> new")
    @PostMapping("/add")
    public @NotNull ResponseEntity<AirData> addAirData(@RequestBody AirData airData,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) @NotNull String token) {
        if (token.equals("password"))
            return new ResponseEntity<>(airDataService.addAirData(airData), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(airData, HttpStatus.BAD_REQUEST);

    }

    @Contract("_ -> new")
    @PutMapping("/update")
    public @NotNull ResponseEntity<AirData> updateAirData(@RequestBody AirData airData) {
        return new ResponseEntity<>(airDataService.updateAirData(airData), HttpStatus.OK);
    }

    @Contract("_ -> new")
    @PutMapping("/update_reportN")
    public @NotNull ResponseEntity<AirData> increaseReportNumberByOne(@RequestBody @NotNull Long id) {
        return new ResponseEntity<>(airDataService.updateNumberReportedById(id), HttpStatus.OK);
    }

    @Contract("_ -> new")
    @DeleteMapping("/delete/{id}")
    public @NotNull ResponseEntity<HttpStatus> updateAirData(@PathVariable("id") Long id) {
        airDataService.deleteAirData(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
