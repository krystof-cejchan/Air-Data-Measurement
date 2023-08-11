package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.domain.nondatabase_objects.AirDataAverage;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.exceptions.AlreadyInvalidData;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.exceptions.GreatTimeDifferenceException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import cz.krystofcejchan.air_quality_measurement.utilities.MathUtils;
import cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table.LeaderboardTable;
import cz.krystofcejchan.air_quality_measurement.utilities.psw.Psw;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.CheckForNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.springframework.http.HttpStatus.*;


/**
 * The type Air data service.
 */
@Service
public class AirDataService {
    @Autowired
    AirDataLeaderboardService leaderboardService;
    @Autowired
    AirDataLeaderboardRepository leaderboardRepository;
    @Autowired
    AirDataRepository airDataRepository;
    @Autowired
    LocationDataRepository locationDataRepository;

    /**
     * Instantiates a new Air data service.
     */
    @Autowired
    @Contract(pure = true)
    public AirDataService() {
    }

    /**
     * Add {@link AirData}.
     * if airData are not valid(values are most likely to be incorrect), nothing will be saved
     *
     * @param airData      the air data
     * @param timeReceived {@link LocalDateTime} the data were firstly received by the back-end (method addAirData in
     *                     {@link cz.krystofcejchan.air_quality_measurement.resource.AirDataResource})
     * @return the air data
     */
    public @NotNull AirData addAirData(@NotNull AirData airData, @NotNull LocalDateTime timeReceived) {
        if (!areDataValid(airData)) return airData;

        airData.setReceivedDataDateTime(timeReceived);
        airData.setRndHash(UUID.randomUUID().toString());

        AirData airDataSave = airDataRepository.save(airData);

        leaderboardService.updateLeaderboard();
        return airDataSave;
    }

    /**
     * Find all air data list.
     *
     * @return the list
     */
    public @NotNull List<AirData> findAllAirData() {
        return airDataRepository.findAll();
    }

    /**
     * Update air data air data.
     *
     * @param airData the air data
     * @return the air data
     */
    public @NotNull AirData updateAirData(AirData airData) {
        return airDataRepository.save(airData);
    }

    /**
     * report a data {@link AirData} - adding +1 to reportedN column in database
     *
     * @param id identifier of the record
     * @return AirData or null if no record matched the id
     */
    @Nullable
    public synchronized AirData reportAirDataById(Long id, @CheckForNull String pswd) {
        var airDataOptional = airDataRepository.findById(id);
        if (airDataOptional.isPresent()) {
            AirData airData = airDataOptional.get();
            airData.setReportedN(airData.getReportedN() + 1);

            List<AirData> previousAirDataList = airDataRepository.findByLocationIdAndReceivedDataDateTimeBefore(airData.getLocationId(),
                            airData.getReceivedDataDateTime())
                    .orElse(Collections.emptyList());

            AirData previousAirData = previousAirDataList.isEmpty() ? airData : previousAirDataList.get(0);
            boolean isAuthRequest = !(pswd == null || pswd.isEmpty() || pswd.isBlank() || !pswd.equals(String.valueOf(Psw.dbpsd)));
            try {
                if (isAuthRequest || !areDataValid(airData) || !compareAirDataObjects(airData, previousAirData)) {
                    airDataRepository.delete(airData);
                    LeaderboardTable.saveChangedDataAndDeleteOldData(leaderboardRepository,
                            leaderboardRepository.findAll(),
                            LeaderboardTable.getFreshDataForLeaderboard((airDataRepository)));
                    return airData;
                }
                return airDataRepository.save(airData);
            } catch (AlreadyInvalidData | GreatTimeDifferenceException exception) {
                return null;
            }
        }
        return null;
    }


    /**
     * Find air data air data.
     *
     * @param id of the data in the database
     * @return AirData {@link AirData} object
     * @throws DataNotFoundException if no record with such an id exists
     */
    public AirData findAirData(Long id) throws DataNotFoundException {
        return airDataRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    }

    /**
     * compares currentAirData and previouslyAirData based on their attributes(airQuality,Temperature,Humidity)
     *
     * @param currentAirData    {@link AirData} #1
     * @param previouslyAirData {@link AirData}#2
     * @return true if the data are acceptable, else false
     * @throws AlreadyInvalidData           currentAirData or previouslyAirData have been marked as invalid data!
     * @throws GreatTimeDifferenceException previous and current AirData measurement time differs more than 2 hours,
     *                                      therefore data cannot be validly compared
     */
    @Contract(pure = true)
    private boolean compareAirDataObjects(@NotNull AirData currentAirData, AirData previouslyAirData) throws AlreadyInvalidData, GreatTimeDifferenceException {
        if (currentAirData.getInvalidData() || previouslyAirData.getInvalidData())
            throw new AlreadyInvalidData();
        if (currentAirData.getReceivedDataDateTime().until(previouslyAirData.getReceivedDataDateTime(),
                ChronoUnit.MINUTES) >= 60 * 2)
            throw new GreatTimeDifferenceException();

        ArrayList<Boolean> airQ_temp_hum = new ArrayList<>();

        airQ_temp_hum.add(Math.abs(currentAirData.getAirQuality().doubleValue() - previouslyAirData.getAirQuality().doubleValue()) < 20);
        airQ_temp_hum.add(Math.abs(currentAirData.getTemperature().doubleValue() - previouslyAirData.getTemperature().doubleValue()) < 10);
        airQ_temp_hum.add(Math.abs(currentAirData.getHumidity().doubleValue() - previouslyAirData.getHumidity().doubleValue()) < 20);

        return airQ_temp_hum.stream().allMatch(Boolean::booleanValue);
    }

    @Contract(pure = true)
    private boolean areDataValid(@NotNull AirData airData1) {
        ArrayList<Boolean> airQ_temp_hum = new ArrayList<>();

        airQ_temp_hum.add(MathUtils.isInBetween(airData1.getAirQuality(), 0, 1_000, false));
        airQ_temp_hum.add(MathUtils.isInBetween(airData1.getTemperature(), -30, 55, true));
        airQ_temp_hum.add(MathUtils.isInBetween(airData1.getHumidity(), 0, 100, true));

        return airQ_temp_hum.stream().allMatch(Boolean::booleanValue);
    }

    /**
     * Gets latest air data.
     *
     * @param paramLocationId the param location
     * @return the latest air data
     */
    public @NotNull ResponseEntity<List<AirData>> getLatestAirData(@Nullable Long paramLocationId) {
        List<LocationData> locationData = locationDataRepository.findAll();
        if (paramLocationId != null) {
            //paramLocationId is set and its value matches at least one LocationData
            try {
                AirData airData = airDataRepository.findByLocationIdOrderByReceivedDataDateTimeDesc(locationDataRepository
                                .findById(paramLocationId).orElseThrow(DataNotFoundException::new))
                        .orElseThrow(DataNotFoundException::new).get(0);
                return new ResponseEntity<>(Collections.singletonList(airData), OK);
            } catch (DataNotFoundException dataNotFoundException) {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(locationData.stream()
                    .filter(location -> !airDataRepository.findByLocationIdOrderByReceivedDataDateTimeDesc(location)
                            .orElseThrow(DataNotFoundException::new).isEmpty())
                    .map(existingLocation -> airDataRepository.findByLocationIdOrderByReceivedDataDateTimeDesc(existingLocation)
                            .orElseThrow(DataNotFoundException::new).get(0))
                    .toList(),
                    OK);
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
        return receivedDate.isEmpty() ? new ResponseEntity<>(BAD_REQUEST) : new ResponseEntity<>(receivedDate.orElseThrow(DataNotFoundException::new).stream().toList(), OK);
    }

    /**
     * @return latest average temperature from all locations
     */
    public @NotNull ResponseEntity<? extends Number> getCurrentAverageTemperature() {
        return new ResponseEntity<>(locationDataRepository.findAll().stream()
                .filter(location -> !airDataRepository.findByLocationIdOrderByReceivedDataDateTimeDesc(location)
                        .orElseThrow(DataNotFoundException::new).isEmpty())
                .map(existingLocation -> airDataRepository.findByLocationIdOrderByReceivedDataDateTimeDesc(existingLocation)
                        .orElseThrow(DataNotFoundException::new).get(0))
                .mapToDouble(it -> it.getTemperature().doubleValue()).average().orElse(Double.NaN), OK);
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
            return new ResponseEntity<>(BAD_REQUEST);

        try {
            BigDecimal airQualityAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new).stream().map(AirData::getAirQuality).mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));
            BigDecimal humidityAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new).stream().map(AirData::getHumidity).mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));
            BigDecimal temperatureAvg = BigDecimal.valueOf(receivedDate.orElseThrow(DataNotFoundException::new).stream().map(AirData::getTemperature).mapToDouble(BigDecimal::doubleValue).average().orElseThrow(DataNotFoundException::new));

            return new ResponseEntity<>(new AirDataAverage(temperatureAvg.setScale(2, RoundingMode.HALF_UP), humidityAvg.setScale(2, RoundingMode.HALF_UP), airQualityAvg.setScale(2, RoundingMode.HALF_UP)), OK);

        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        }
    }

    /**
     * Ger air data for one specific day response entity.
     *
     * @param day the day
     * @return the response entity
     */
    @Contract("_ -> new")
    public @NotNull ResponseEntity<?> gerAirDataForOneSpecificDay(LocalDate day) {
        Optional<List<AirData>> receivedDate = airDataRepository
                .findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN),
                        LocalDateTime.of(day, LocalTime.MAX));

        if (receivedDate.orElseThrow(DataNotFoundException::new).isEmpty())
            return new ResponseEntity<>(BAD_REQUEST);

        return new ResponseEntity<>(receivedDate, OK);
    }

    public @NotNull ResponseEntity<? extends Collection<?>> gerAirDataForDateRange(LocalDate start, LocalDate end) {
        Optional<List<AirData>> receivedDate = airDataRepository
                .findByReceivedDataDateTimeBetween(LocalDateTime.of(start, LocalTime.MIN),
                        LocalDateTime.of(end, LocalTime.MAX));

        if (receivedDate.orElseThrow(DataNotFoundException::new).isEmpty())
            return new ResponseEntity<>(TOO_EARLY);

        return new ResponseEntity<>(receivedDate.get(), OK);
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
            return new ResponseEntity<>(BAD_REQUEST);
        else return new ResponseEntity<>(receivedDate, OK);
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

