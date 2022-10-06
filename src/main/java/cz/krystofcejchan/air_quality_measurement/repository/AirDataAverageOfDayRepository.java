package cz.krystofcejchan.air_quality_measurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirDataAverageOfDayRepository extends JpaRepository<cz.krystofcejchan.air_quality_measurement.domain.AirDataAverageOfDay, Long> {

}
