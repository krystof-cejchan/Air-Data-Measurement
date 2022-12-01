package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay;
import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataAverageOfDayRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The type Air data average of day service.
 */
@Service
public record AirDataAverageOfDayService(
        AirDataAverageOfDayRepository avgRepository,
        AirDataRepository airDataRepository,
        LocationDataRepository locationDataRepository) {
    /**
     * Instantiates a new Air data average of day service.
     *
     * @param avgRepository     the avg airDataRepository
     * @param airDataRepository the air data airDataRepository
     */
    @Autowired
    @Contract(pure = true)
    public AirDataAverageOfDayService {
    }

    /**
     * Adds and saves AirDataAverage
     *
     * @param airDataAverageOfDay the air data average of day
     */
    public void addAirDataAverageOfDay(@NotNull AirDataAverageOfDay airDataAverageOfDay) {
        avgRepository.save(airDataAverageOfDay);
    }

    /**
     * Gets average air data for one specific day.
     *
     * @param day the day
     * @return the average air data for one specific day
     */
    public Optional<HashMap<LocationData, AirDataAverageOfDay>> getAverageAirDataForOneSpecificDay(java.time.LocalDate day) {
        List<LocationData> locationDataList = locationDataRepository.findAll();
        Optional<List<AirData>> receivedData = airDataRepository
                .findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN),
                        LocalDateTime.of(day, LocalTime.MAX));

        if (receivedData.orElseThrow(DataNotFoundException::new).isEmpty())
            return Optional.empty();

        List<LocationData> locationToBeExcluded = new ArrayList<>();
        locationDataList.forEach(location -> avgRepository
                .findByLocationAndReceivedDataDate(location, day)
                .ifPresent(avgData -> locationToBeExcluded
                        .addAll(avgData.stream()
                                .map(AirDataAverageOfDay::getLocation)
                                .toList())));

        List<LocationData> locationList = receivedData.get().stream().map(AirData::getLocationId)
                .filter(airData_getLocationId -> locationToBeExcluded.stream()
                        .noneMatch(locationExclusion -> locationExclusion.equals(airData_getLocationId)))
                .toList();

        List<AirData> validAirData = receivedData.get().stream().filter(location -> locationList
                        .stream().anyMatch(locationMatch -> locationMatch.equals(location.getLocationId())))
                .toList();

        HashMap<LocationData, AirDataAverageOfDay> hashMapLocToAvgAirData = new HashMap<>();
        for (LocationData location : locationList) {
            List<AirData> validAirDataFilterToLocation = validAirData.stream()
                    .filter(airData -> airData.getLocationId().equals(location)).toList();

            BigDecimal airQualityAvg = BigDecimal.valueOf(validAirDataFilterToLocation.stream()
                    .map(AirData::getAirQuality)
                    .mapToDouble(BigDecimal::doubleValue)
                    .average()
                    .orElse(Double.NaN)).setScale(2, RoundingMode.HALF_UP);

            BigDecimal humidityAvg = BigDecimal.valueOf(validAirDataFilterToLocation.stream()
                    .map(AirData::getHumidity)
                    .mapToDouble(BigDecimal::doubleValue)
                    .average()
                    .orElse(Double.NaN)).setScale(2, RoundingMode.HALF_UP);

            BigDecimal temperatureAvg = BigDecimal.valueOf(validAirDataFilterToLocation.stream()
                    .map(AirData::getTemperature)
                    .mapToDouble(BigDecimal::doubleValue)
                    .average()
                    .orElse(Double.NaN)).setScale(2, RoundingMode.HALF_UP);

            hashMapLocToAvgAirData.putIfAbsent(location, new AirDataAverageOfDay(
                    -0L,
                    location,
                    day,
                    airQualityAvg,
                    temperatureAvg,
                    humidityAvg));
        }
        return Optional.of(hashMapLocToAvgAirData);
    }

    /**
     * Gets all avg air data.
     *
     * @return the all avg air data
     */
    public List<AirDataAverageOfDay> getAllAvgAirData() {
        return avgRepository.findAll();
    }
}
