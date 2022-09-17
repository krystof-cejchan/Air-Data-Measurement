package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirDataRepository extends JpaRepository<AirData, Long> {

}
