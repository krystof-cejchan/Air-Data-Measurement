package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirDataLeaderboardRepository extends JpaRepository<AirDataLeaderboard, Long> {
    Optional<List<AirDataLeaderboard>> findByLocationIdAndLeaderboardType(LocationData locationId, LeaderboardType leaderboardType);

    Optional<List<AirDataLeaderboard>> findTop3ByLeaderboardType(LeaderboardType leaderboardType);
}
