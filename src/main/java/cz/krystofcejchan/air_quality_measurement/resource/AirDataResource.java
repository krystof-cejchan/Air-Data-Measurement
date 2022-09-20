package cz.krystofcejchan.air_quality_measurement.resource;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.service.AirDataService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airdata")
public class AirDataResource {
    private final AirDataService airDataService;

    public AirDataResource(AirDataService airDataService) {
        this.airDataService = airDataService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirData>> getAllAirData() {
        return new ResponseEntity<>(airDataService.findAllAirData(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getAirDataById(@PathVariable("id") Long id) {
        if (airDataService.existAirData(id))
            return new ResponseEntity<>(airDataService.findAirData(id), HttpStatus.OK);
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existAirData(@PathVariable("id") Long id) {
        return new ResponseEntity<>(airDataService.existAirData(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AirData> addAirData(@RequestBody AirData airData,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        // if token is correct...
        return new ResponseEntity<>(airDataService.addAirData(airData), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<AirData> updateAirData(@RequestBody AirData airData) {
        return new ResponseEntity<>(airDataService.updateAirData(airData), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> updateAirData(@PathVariable("id") Long id) {
        airDataService.deleteAirData(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
