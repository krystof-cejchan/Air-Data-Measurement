package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface Air data repository.
 */
@Repository
public interface AirDataRepository extends JpaRepository<AirData, Long> {

    /**
     * Find by location order by received data date time desc optional.
     *
     * @param location the location
     * @return the optional
     */
    Optional<List<AirData>> findByLocationOrderByReceivedDataDateTimeDesc(Location location);

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
     * @param location    the location
     * @param endTimeLine the end time line
     * @return the optional
     */
    Optional<List<AirData>> findByLocationAndReceivedDataDateTimeBefore(Location location, LocalDateTime endTimeLine);

    /**
     * Find top 3 temperature distinct by location order by air quality asc optional.
     *
     * @param location the location
     * @return the optional
     */
    Optional<List<AirData>> findTop3TemperatureByLocationOrderByTemperatureAsc(Location location);

    /**
     * Find top 3 temperature distinct by location order by air quality desc optional.
     *
     * @param location the location
     * @return the optional
     */
    Optional<List<AirData>> findTop3TemperatureByLocationOrderByTemperatureDesc(Location location);

    /**
     * Find top 3 humidity distinct by location order by air quality desc optional.
     *
     * @param location the location
     * @return the optional
     */
    Optional<List<AirData>> findTop3HumidityByLocationOrderByHumidityDesc(Location location);

    /**
     * Find top 3 humidity distinct by location order by air quality asc optional.
     *
     * @param location the location
     * @return the optional
     */
    Optional<List<AirData>> findTop3HumidityByLocationOrderByHumidityAsc(Location location);

    /**
     * Find top 3 air quality distinct by location order by air quality asc optional.
     *
     * @param location the location
     * @return the optional
     */
    Optional<List<AirData>> findTop3AirQualityByLocationOrderByAirQualityAsc(Location location);

    /**
     * Find top 3 air quality distinct by location order by air quality desc optional.
     *
     * @param location the location
     * @return the optional
     */
    Optional<List<AirData>> findTop3AirQualityByLocationOrderByAirQualityDesc(Location location);


}
