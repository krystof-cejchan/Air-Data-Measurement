package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface Air data repository.
 */
@Repository
public interface AirDataRepository extends JpaRepository<AirData, Long> {

    /**
     * Find by location order by received data date time desc optional.
     *
     * @param locationId the location
     * @return the optional
     */
    Optional<List<AirData>> findByLocationIdOrderByReceivedDataDateTimeDesc(LocationData locationId);

    /**
     * Find by received data date time between optional.
     *
     * @param start the start
     * @param end   the end
     * @return the optional
     */
    Optional<List<AirData>> findByReceivedDataDateTimeBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Find by id and rnd hash optional.
     *
     * @param id      the id
     * @param rndHash the rnd hash
     * @return the optional
     */
    Optional<AirData> findByIdAndRndHash(Long id, String rndHash);

    /**
     * Find by location and received data date time before optional.
     *
     * @param locationId  the location
     * @param endTimeLine the end time line
     * @return the optional
     */
    Optional<AirData> findTopByLocationIdAndReceivedDataDateTimeBeforeOrderByReceivedDataDateTimeDesc(LocationData locationId, LocalDateTime endTimeLine);

    /**
     * default method created for name convenience
     * @param locationId {@link LocationData}
     * @param endTimeLine {@link LocalDateTime} for filtering
     * @return the result of {@code findTopByLocationIdAndReceivedDataDateTimeBeforeOrderByReceivedDataDateTimeDesc}
     * located in this interface
     */
    default Optional<AirData> findLatestByLocationAndBeforeGivenTime(LocationData locationId, LocalDateTime endTimeLine) {
        return findTopByLocationIdAndReceivedDataDateTimeBeforeOrderByReceivedDataDateTimeDesc(locationId, endTimeLine);
    }

    /**
     * Find top 3 temperature distinct by location order by air quality asc optional.
     *
     * @return the optional
     */
    Optional<Set<AirData>> findTop3TemperatureDistinctByOrderByTemperatureAsc();

    /**
     * Find top 3 temperature distinct by location order by air quality desc optional.
     *
     * @return the optional
     */
    Optional<Set<AirData>> findTop3TemperatureByOrderByTemperatureDesc();

    /**
     * Find top 3 humidity distinct by location order by air quality desc optional.
     *
     * @return the optional
     */
    Optional<Set<AirData>> findTop3HumidityByOrderByHumidityDesc();

    /**
     * Find top 3 humidity distinct by location order by air quality asc optional.
     *
     * @return the optional
     */
    Optional<Set<AirData>> findTop3HumidityByOrderByHumidityAsc();

    /**
     * Find top 3 air quality distinct by location order by air quality asc optional.
     *
     * @return the optional
     */
    Optional<Set<AirData>> findTop3AirQualityByOrderByAirQualityAsc();

    /**
     * Find top 3 air quality distinct by location order by air quality desc optional.
     *
     * @return the optional
     */
    Optional<Set<AirData>> findTop3AirQualityByOrderByAirQualityDesc();


}
