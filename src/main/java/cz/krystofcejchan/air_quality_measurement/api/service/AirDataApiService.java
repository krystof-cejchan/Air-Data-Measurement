package cz.krystofcejchan.air_quality_measurement.api.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.other_objects.AirDataAverage;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.service.AirDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<?> getAverageAirDataFromDateToDate(LocalDateTime start, LocalDateTime end) {
        Optional<List<AirData>> receivedDate = airDataRepository.findByReceivedDataDateTimeBetween(start, end);
        return receivedDate.isEmpty() ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(receivedDate.orElseThrow(DataNotFoundException::new)
                        .stream().toList(), HttpStatus.OK);
    }

    public ResponseEntity<?> gerAverageAirDataForOneSpecificDay(java.time.LocalDate day) {
        Optional<List<AirData>> receivedDate = airDataRepository
                .findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN),
                        LocalDateTime.of(day, LocalTime.MAX));

        if (receivedDate.orElseThrow(DataNotFoundException::new).isEmpty())
            return new ResponseEntity<AirDataAverage>(new AirDataAverage(null, null, null),
                    HttpStatus.OK);

        try {
            BigDecimal airQualityAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new)
                    .stream().map(AirData::getAirQuality)
                    .mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));
            BigDecimal humidityAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new)
                    .stream().map(AirData::getHumidity)
                    .mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));
            BigDecimal temperatureAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new)
                    .stream().map(AirData::getTemperature)
                    .mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));

            return new ResponseEntity<>(
                    new AirDataAverage(temperatureAvg.setScale(2, RoundingMode.HALF_UP),
                            humidityAvg.setScale(2, RoundingMode.HALF_UP),
                            airQualityAvg.setScale(2, RoundingMode.HALF_UP)),
                    HttpStatus.OK);

        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase() +
                    '\n' + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> gerAirDataForOneSpecificDay(java.time.LocalDate day) {
        Optional<List<AirData>> receivedDate = airDataRepository
                .findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN),
                        LocalDateTime.of(day, LocalTime.MAX));

        if (receivedDate.orElseThrow(DataNotFoundException::new).isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(receivedDate, HttpStatus.OK);
    }
}