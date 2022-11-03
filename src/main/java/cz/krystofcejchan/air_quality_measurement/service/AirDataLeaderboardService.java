package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirDataLeaderboardService {
    private AirDataLeaderboardRepository repository;

    public AirDataLeaderboardService(AirDataLeaderboardRepository airDataLeaderboardRepository) {
        this.repository = airDataLeaderboardRepository;
    }

    @Autowired
    public AirDataLeaderboardService() {
    }

    public Optional<List<AirDataLeaderboard>> getAirDataLeaderboardByLocationAndLeaderboardType(@NotNull Location location,
                                                                                                @NotNull LeaderboardType leaderboardType) {
        return repository.findByLocationAndLeaderboardType(location, leaderboardType);
    }
}
