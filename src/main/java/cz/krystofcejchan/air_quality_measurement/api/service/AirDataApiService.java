package cz.krystofcejchan.air_quality_measurement.api.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.nondatabase_objects.AirDataAverage;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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

/**
 * The type Air data api service.
 */
@Service
public record AirDataApiService(AirDataRepository airDataRepository) {
    /**
     * Instantiates a new Air data api service.
     *
     * @param airDataRepository the air data repository
     */
    @Contract(pure = true)
    @Autowired
    public AirDataApiService {
    }

    /**
     * Gets latest air data.
     *
     * @param paramLocation the param location
     * @return the latest air data
     */
    @Contract("null -> new")
    public @NotNull ResponseEntity<?> getLatestAirData(String paramLocation) {
        if (paramLocation != null && Location.toList().stream().map(Enum::toString).anyMatch(location -> location.equals(paramLocation))) {
            //paramLocation is set and its value matches at least one LocationData
            try {
                AirData airData = airDataRepository.findByLocationOrderByReceivedDataDateTimeDesc(Location.of(paramLocation)).orElseThrow(DataNotFoundException::new).get(0);
                return new ResponseEntity<>(airData, HttpStatus.OK);
            } catch (DataNotFoundException dataNotFoundException) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Location.toList().stream().filter(location -> !airDataRepository.findByLocationOrderByReceivedDataDateTimeDesc(location).orElseThrow(DataNotFoundException::new).isEmpty()).map(existingLocation -> airDataRepository.findByLocationOrderByReceivedDataDateTimeDesc(existingLocation).orElseThrow(DataNotFoundException::new).get(0)).toList(), HttpStatus.OK);
        }
    }

    /**
     * Gets average air data from date to date.
     *
     * @param start the start
     * @param end   the end
     * @return the average air data from date to date
     */
    public @NotNull ResponseEntity<?> getAverageAirDataFromDateToDate(LocalDateTime start, LocalDateTime end) {
        Optional<List<AirData>> receivedDate = airDataRepository.findByReceivedDataDateTimeBetween(start, end);
        return receivedDate.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST) : new ResponseEntity<>(receivedDate.orElseThrow(DataNotFoundException::new).stream().toList(), HttpStatus.OK);
    }

    /**
     * Gets average air data for one specific day.
     *
     * @param day the day
     * @return the average air data for one specific day
     */
    @Contract("_ -> new")
    public @NotNull ResponseEntity<?> getAverageAirDataForOneSpecificDay(java.time.LocalDate day) {
        Optional<List<AirData>> receivedDate = airDataRepository.findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN), LocalDateTime.of(day, LocalTime.MAX));

        if (receivedDate.orElseThrow(DataNotFoundException::new).isEmpty())
            return new ResponseEntity<>(new AirDataAverage(null, null, null), HttpStatus.OK);

        try {
            BigDecimal airQualityAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new).stream().map(AirData::getAirQuality).mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));
            BigDecimal humidityAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new).stream().map(AirData::getHumidity).mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));
            BigDecimal temperatureAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new).stream().map(AirData::getTemperature).mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));

            return new ResponseEntity<>(new AirDataAverage(temperatureAvg.setScale(2, RoundingMode.HALF_UP), humidityAvg.setScale(2, RoundingMode.HALF_UP), airQualityAvg.setScale(2, RoundingMode.HALF_UP)), HttpStatus.OK);

        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase() + '\n' + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Ger air data for one specific day response entity.
     *
     * @param day the day
     * @return the response entity
     */
    @Contract("_ -> new")
    public @NotNull ResponseEntity<?> gerAirDataForOneSpecificDay(java.time.LocalDate day) {
        Optional<List<AirData>> receivedDate = airDataRepository.findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN), LocalDateTime.of(day, LocalTime.MAX));

        if (receivedDate.orElseThrow(DataNotFoundException::new).isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(receivedDate, HttpStatus.OK);
    }

    /**
     * Gets data by id and hash.
     *
     * @param id   the id
     * @param hash the hash
     * @return the data by id and hash
     */
    @Contract("_, _ -> new")
    public @NotNull ResponseEntity<?> getDataByIdAndHash(Long id, String hash) {
        Optional<AirData> receivedDate = airDataRepository.findByIdAndRndHash(id, hash);

        if (receivedDate.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(receivedDate, HttpStatus.OK);
    }

    /**
     * Gets leader board data.
     *
     * @param leaderboardType the leaderboard type
     * @return the leader board data
     */
    public Optional<List<AirData>> getLeaderBoardData(LeaderboardType leaderboardType) {
        return switch (leaderboardType) {
            case HIGHEST_AIRQ -> airDataRepository.findTop3AirQualityByOrderByAirQualityDesc();
            case LOWEST_AIRQ -> airDataRepository.findTop3AirQualityByOrderByAirQualityAsc();
            case HIGHEST_TEMP -> airDataRepository.findTop3TemperatureByOrderByTemperatureDesc();
            case LOWEST_TEMP -> airDataRepository.findTop3TemperatureDistinctByOrderByTemperatureAsc();
            case HIGHEST_HUM -> airDataRepository.findTop3HumidityByOrderByHumidityDesc();
            case LOWEST_HUM -> airDataRepository.findTop3HumidityByOrderByHumidityAsc();
        };
    }
}