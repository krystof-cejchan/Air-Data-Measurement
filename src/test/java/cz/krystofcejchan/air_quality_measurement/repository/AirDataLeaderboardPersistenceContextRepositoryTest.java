package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class AirDataLeaderboardPersistenceContextRepositoryTest {

    @Test
    void insertWithEntityManager() {

        AirDataLeaderboard airDataLeaderboard = new AirDataLeaderboard(1L,
                new AirData((byte) 0),
                LeaderboardType.HIGHEST_AIRQ,
                new LocationData(),
                1);
        assertThatExceptionOfType(EntityExistsException.class).isThrownBy(() -> {
            //airDataLeaderboardPersistenceContextRepository.insertWithEntityManager(airDataLeaderboard);
        });
    }
}