package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.api.service.AirDataApiService;
import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
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

    public Optional<List<AirDataLeaderboard>> getAirDataLeaderboardByLocationAndLeaderboardType(@NotNull Location location,
                                                                                                @NotNull LeaderboardType leaderboardType) {
        return airDataLeaderboardRepository.findByLocationAndLeaderboardType(location, leaderboardType);
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


    @Contract(pure = true)
    public void updateLeaderboard() {
        try {
            Thread.sleep(700);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AirDataLeaderboard> a = new ArrayList<>();
        getTop3AirDataLeaderboardForEachLeaderboardType().forEach((key, value) -> {
            Optional<List<AirDataLeaderboard>> airDataLeaderboards = airDataLeaderboardRepository.findTop3ByLeaderboardType(key);
            Optional<List<AirData>> airDataList = new AirDataApiService(airDataRepository).getLeaderBoardData(key);
            if (airDataLeaderboards.isEmpty() || airDataList.isEmpty()) return;

            if (!airDataLeaderboards.get()
                    .stream()
                    .map(AirDataLeaderboard::getAirDataId)
                    .allMatch(airData -> airDataList.get()
                            .stream()
                            .map(AirData::getId)
                            .allMatch(id -> id.equals(airData.getId())))
            ) {
                airDataLeaderboardRepository.deleteAll(airDataLeaderboards.get());
                List<AirDataLeaderboard> airDataLeaderboardListToBeSavedToDatabase = airDataList.get()
                        .stream()
                        .map(airData -> airDataLeaderboards.get()
                                .stream()
                                .map(airDataLeaderboard ->
                                        new AirDataLeaderboard(airData, key,
                                                airData.getLocation(),
                                                airDataList.get().indexOf(airData) + 1)).toList())
                        .flatMap(List::stream).distinct()
                        .toList();
                airDataLeaderboardListToBeSavedToDatabase.forEach(i -> System.out.println(i.toString()));
                a.addAll(airDataLeaderboardListToBeSavedToDatabase);

                // TODO does not work!!!
            }
        });
        a.stream().distinct().forEach(i -> System.out.println(i.toString()));
    }


    @Contract(" -> new")
    public @NotNull Optional<List<AirDataLeaderboard>> getAllDataFromLeaderboard() {
        return airDataLeaderboardRepository.findAll().isEmpty() ? Optional.empty() :
                Optional.of(airDataLeaderboardRepository.findAll());
    }
}
