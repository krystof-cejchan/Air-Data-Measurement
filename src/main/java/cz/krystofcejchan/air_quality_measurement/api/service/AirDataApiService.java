package cz.krystofcejchan.air_quality_measurement.api.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.service.AirDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AirDataApiService extends AirDataService {
    private final AirDataRepository airDataRepository;

    @Autowired
    public AirDataApiService(AirDataRepository airDataRepository) {
        super(airDataRepository);
        this.airDataRepository = airDataRepository;
    }

    public ResponseEntity<?> getLatestAirData(String paramLocation) {
        if (paramLocation != null && Location.toList()
                .stream().map(Enum::toString)
                .anyMatch(location -> location.equals(paramLocation))) {
            //paramLocation is set and its value matches at least one Location
            try {
                AirData airData = airDataRepository.findByLocationOrderByReceivedDataDateTimeDesc(Location.of(paramLocation))
                        .orElseThrow(DataNotFoundException::new).get(0);
                return new ResponseEntity<>(airData, HttpStatus.OK);
            } catch (DataNotFoundException dataNotFoundException) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Location.toList()
                    .stream()
                    .filter(location -> !airDataRepository.findByLocationOrderByReceivedDataDateTimeDesc(location)
                            .orElseThrow(DataNotFoundException::new).isEmpty())
                    .map(existingLocation -> airDataRepository.findByLocationOrderByReceivedDataDateTimeDesc(existingLocation)
                            .orElseThrow(DataNotFoundException::new).get(0)).toList(),
                    HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getAirDataFromDateToDate(LocalDateTime start, LocalDateTime end) {
        return airDataRepository.findByReceivedDataDateTimeBetween(start, end).isEmpty() ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(airDataRepository.findByReceivedDataDateTimeBetween(start, end)
                        .orElseThrow(DataNotFoundException::new).stream().toList(), HttpStatus.OK);

    }
}
