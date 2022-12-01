package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table.LeaderboardTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AirDataLeaderboardService {

    @Autowired
    AirDataLeaderboardRepository airDataLeaderboardRepository;
    @Autowired
    AirDataRepository airDataRepository;

    @Autowired
    @Contract(pure = true)
    public AirDataLeaderboardService() {
    }

    @Contract(pure = true)
    public AirDataLeaderboardService(AirDataLeaderboardRepository airDataLeaderboardRepository) {
        this.airDataLeaderboardRepository = airDataLeaderboardRepository;
    }

    public Optional<List<AirDataLeaderboard>> getAirDataLeaderboardByLocationAndLeaderboardType(@NotNull LocationData location,
                                                                                                @NotNull LeaderboardType leaderboardType) {
        return airDataLeaderboardRepository.findByLocationIdAndLeaderboardType(location, leaderboardType);
    }

    public Optional<List<AirDataLeaderboard>> getTop3AirDataLeaderboardByLeaderboardType(@NotNull LeaderboardType leaderboardType) {
        return airDataLeaderboardRepository.findTop3ByLeaderboardType(leaderboardType);
    }

    @Contract(pure = true)
    private @NotNull Map<LeaderboardType, List<AirDataLeaderboard>> getTop3AirDataLeaderboardForEachLeaderboardType() {
        Map<LeaderboardType, List<AirDataLeaderboard>> leaderboardTypeOptionalMap = new HashMap<>();

        for (LeaderboardType leaderboardType : LeaderboardType.values()) {
            leaderboardTypeOptionalMap.putIfAbsent(leaderboardType, airDataLeaderboardRepository
                    .findTop3ByLeaderboardType(leaderboardType)
                    .orElse(Collections.singletonList(null))
                    .stream()
                    .filter(Objects::nonNull)
                    .toList());
        }
        return leaderboardTypeOptionalMap;
    }

    /**
     * method called upon new record inserted into the AirData table in the db.
     * <br> this method should verify whether newly inserted record should be included in the leaderboard table
     * by any of its parameters
     */
    @Contract(pure = true)
    public void updateLeaderboard() {
       /* try {
            Thread.sleep(700);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        LeaderboardTable.saveChangedDataAndDeleteOldData(airDataLeaderboardRepository,
                airDataLeaderboardRepository.findAll(), LeaderboardTable.getFreshDataForLeaderboard(airDataRepository));

    }


    @Contract(" -> new")
    public @NotNull Optional<List<AirDataLeaderboard>> getAllDataFromLeaderboard() {
        List<AirDataLeaderboard> airDataLeaderboardRepositoryAll = airDataLeaderboardRepository.findAll();
        return airDataLeaderboardRepositoryAll.isEmpty() ? Optional.empty() :
                Optional.of(airDataLeaderboardRepositoryAll);
    }
}