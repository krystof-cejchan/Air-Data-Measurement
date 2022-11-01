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

/**
 * The type Air data resource.
 */
@RestController
@RequestMapping("/airdata")
public record AirDataResource(AirDataService airDataService) {
    /**
     * Instantiates a new Air data resource.
     *
     * @param airDataService the air data service
     */
    @Contract(pure = true)
    public AirDataResource {
    }

    /**
     * Gets all air data.
     *
     * @return the all air data
     */
    @Contract(" -> new")
    @GetMapping("/all")
    public @NotNull ResponseEntity<List<AirData>> getAllAirData() {
        return new ResponseEntity<>(airDataService.findAllAirData(), HttpStatus.OK);
    }

    /**
     * Gets air data by id.
     *
     * @param id the id
     * @return the air data by id
     */
    @GetMapping("/find/{id}")
    public @NotNull ResponseEntity<?> getAirDataById(@PathVariable("id") Long id) {
        if (airDataService.existAirData(id))
            return new ResponseEntity<>(airDataService.findAirData(id), HttpStatus.OK);
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    /**
     * Exist air data response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Contract("_ -> new")
    @GetMapping("/exists/{id}")
    public @NotNull ResponseEntity<Boolean> existAirData(@PathVariable("id") Long id) {
        return new ResponseEntity<>(airDataService.existAirData(id), HttpStatus.OK);
    }

    /**
     * Add air data response entity.
     *
     * @param airData the air data
     * @param token   the token
     * @return the response entity
     */
    @Contract("_, _ -> new")
    @PostMapping("/add")
    public @NotNull ResponseEntity<AirData> addAirData(@RequestBody AirData airData,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) @NotNull String token) {
        if (token.equals("password"))
            return new ResponseEntity<>(airDataService.addAirData(airData), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(airData, HttpStatus.BAD_REQUEST);

    }

    /**
     * Update air data response entity.
     *
     * @param airData the air data
     * @return the response entity
     */
    @Contract("_ -> new")
    @PutMapping("/update")
    public @NotNull ResponseEntity<AirData> deleteAirData(@RequestBody AirData airData) {
        return new ResponseEntity<>(airDataService.updateAirData(airData), HttpStatus.OK);
    }

    /**
     * Increase report number by one response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Contract("_ -> new")
    @PutMapping("/update_reportN")
    public @NotNull ResponseEntity<AirData> increaseReportNumberByOne(@RequestBody @NotNull Long id) {
        return new ResponseEntity<>(airDataService.updateNumberReportedById(id), HttpStatus.OK);
    }

    /**
     * Update air data response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Contract("_ -> new")
    @DeleteMapping("/delete/{id}")
    public @NotNull ResponseEntity<HttpStatus> deleteAirData(@PathVariable("id") Long id) {
        airDataService.deleteAirData(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
