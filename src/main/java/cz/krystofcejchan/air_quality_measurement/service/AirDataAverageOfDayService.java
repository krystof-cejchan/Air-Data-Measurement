package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay;
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

    public Optional<AirDataAverageOfDay> getAverageAirDataForOneSpecificDay(java.time.LocalDate day) {
        Optional<List<AirData>> receivedDate = airDataRepository
                .findByReceivedDataDateTimeBetween(LocalDateTime.of(day, LocalTime.MIN),
                        LocalDateTime.of(day, LocalTime.MAX));

        if (receivedDate.orElseThrow(DataNotFoundException::new).isEmpty())
            return Optional.empty();

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

            return Optional.of(new AirDataAverageOfDay(
                    0L,
                    receivedDate.orElseThrow(DataNotFoundException::new).get(0).getLocation(),
                    day,
                    temperatureAvg.setScale(2, RoundingMode.HALF_UP),
                    humidityAvg.setScale(2, RoundingMode.HALF_UP),
                    airQualityAvg.setScale(2, RoundingMode.HALF_UP)));

        } catch (DataNotFoundException e) {
            return Optional.empty();
        }
    }
}
