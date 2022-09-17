package cz.krystofcejchan.air_quality_measurement;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.service.AirDataService;
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
    public ResponseEntity<AirData> getAirDataById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(airDataService.findAirData(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AirData> addAirData(@RequestBody AirData airData) {
        return new ResponseEntity<>(airDataService.addAirData(airData), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<AirData> updateAirData(@RequestBody AirData airData) {
        return new ResponseEntity<>(airDataService.updateAirData(airData), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> updateAirData(@PathVariable("id") Long id) {
        airDataService.deleteAirData(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
