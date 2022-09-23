package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AirDataRepository extends JpaRepository<AirData, Long> {

    Optional<List<AirData>> findByLocationOrderByReceivedDataDateTimeDesc(Location location);

    Optional<List<AirData>> findByReceivedDataDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
