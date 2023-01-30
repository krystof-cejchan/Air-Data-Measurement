package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.calc_avg;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay;
import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.service.AirDataAverageOfDayService;
import cz.krystofcejchan.air_quality_measurement.utilities.ZonedDateUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

public class CalcAvgFactory {


    @Contract("_ -> new")
    public static @NotNull ResponseEntity<?> calc(@NotNull AirDataAverageOfDayService service) {
        Optional<HashMap<LocationData, AirDataAverageOfDay>> optionalAirDataAverageOfDay
                = service.getAverageAirDataForOneSpecificDay(LocalDate.now(ZonedDateUtils.getPragueZoneId()).minusDays(1));

        HttpStatus resultCode = HttpStatus.ACCEPTED;

        if (optionalAirDataAverageOfDay.isPresent() && !optionalAirDataAverageOfDay.get().isEmpty()) {
            optionalAirDataAverageOfDay.ifPresent(
                    (map) -> map.keySet().forEach(key -> service.addAirDataAverageOfDay(map.get(key))));
        } else
            resultCode = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(resultCode.getReasonPhrase(), resultCode);
    }
}
