package cz.krystofcejchan.air_quality_measurement.repository;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirDataLeaderboardRepository extends JpaRepository<AirDataLeaderboard, Long> {
    Optional<List<AirDataLeaderboard>> findByLocationAndLeaderboardType(Location location, LeaderboardType leaderboardType);
}
