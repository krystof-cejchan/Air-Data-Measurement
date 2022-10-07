package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AirDataAverageOfDayRepository extends JpaRepository<AirDataAverageOfDay, Long> {
    Optional<List<AirDataAverageOfDay>> findByLocationAndReceivedDataDate(Location location, LocalDate receivedDataDate);
}
