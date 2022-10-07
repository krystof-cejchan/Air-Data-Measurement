package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.exceptions.DataNotFoundException;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataAverageOfDayRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
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

@Service
public class AirDataAverageOfDayService {
    private final AirDataAverageOfDayRepository avgRepository;
    private final AirDataRepository airDataRepository;

    @Autowired
    @Contract(pure = true)
    public AirDataAverageOfDayService(AirDataAverageOfDayRepository avgRepository, AirDataRepository airDataRepository) {
        this.avgRepository = avgRepository;
        this.airDataRepository = airDataRepository;
    }

    public AirDataAverageOfDay addAirData(@NotNull AirDataAverageOfDay airDataAverageOfDay) {
        return avgRepository.save(airDataAverageOfDay);
    }

    public Optional<HashMap<Location, AirDataAverageOfDay>> getAverageAirDataForOneSpecificDay(java.time.LocalDate day) {
        Optional<List<AirData>> receivedData = airDataRepository
                .findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN),
                        LocalDateTime.of(day, LocalTime.MAX));

        if (receivedData.orElseThrow(DataNotFoundException::new).isEmpty())
            return Optional.empty();

        List<Location> locationToBeExcluded = new ArrayList<>();
        Location.toList().forEach(location -> avgRepository
                .findByLocationAndReceivedDataDate(location, day)
                .ifPresent(avgData -> locationToBeExcluded
                        .addAll(avgData.stream()
                                .map(AirDataAverageOfDay::getLocation)
                                .toList())));

        List<Location> locationList = receivedData.get().stream().map(AirData::getLocation)
                .filter(location -> Location.toList()
                        .stream().anyMatch(locationMatch -> locationMatch.equals(location)))
                .filter(l -> locationToBeExcluded.stream().noneMatch(lExcl -> lExcl.equals(l)))
                .toList();

        List<AirData> validAirData = receivedData.get().stream().filter(location -> locationList
                        .stream().anyMatch(locationMatch -> locationMatch.equals(location.getLocation())))
                .toList();

        HashMap<Location, AirDataAverageOfDay> hashMapLocToAvgAirData = new HashMap<>();
        for (Location location : locationList) {
            List<AirData> validAirDataFilterToLocation = validAirData.stream()
                    .filter(airData -> airData
                            .getLocation().equals(location)).toList();

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
                    0L,
                    location,
                    day,
                    airQualityAvg,
                    temperatureAvg,
                    humidityAvg));
        }
        return Optional.of(hashMapLocToAvgAirData);
    }

    public List<AirDataAverageOfDay> getAllAvgAirData() {
        return avgRepository.findAll();
    }
}
