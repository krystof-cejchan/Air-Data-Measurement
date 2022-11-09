package cz.krystofcejchan.air_quality_measurement.service;

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
public record AirDataLeaderboardService(AirDataLeaderboardRepository airDataLeaderboardRepository) {

    @Autowired
    static AirDataRepository airDataRepository;

    @Autowired
    @Contract(pure = true)
    public AirDataLeaderboardService {
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
        getTop3AirDataLeaderboardForEachLeaderboardType().forEach((k, v) -> {
            Optional<List<AirDataLeaderboard>> airDataLeaderboards = airDataLeaderboardRepository.findTop3ByLeaderboardType(k);
            Optional<List<AirData>> airDataList = airDataRepository.findTop3ByLeaderboardType(k);
            if (airDataLeaderboards.isEmpty() || airDataList.isEmpty()) return;

            if (!airDataLeaderboards.get()
                    .stream()
                    .map(AirDataLeaderboard::getAirDataId)
                    .allMatch(m -> airDataList.get()
                            .stream()
                            .map(AirData::getId)
                            .allMatch(it -> it.equals(m.getId())))
            ) {
                airDataLeaderboardRepository.deleteAll(airDataLeaderboards.get());
                List<AirDataLeaderboard> a = airDataList.get()
                        .stream()
                        .map(it -> airDataLeaderboards.get()
                                .stream()
                                .map(t ->
                                        new AirDataLeaderboard(it, t.getLeaderboardType(),
                                                t.getLocation(), t.getPosition())).toList())
                        .flatMap(List::stream)
                        .toList();
               // airDataLeaderboardRepository.

                // (musí se najít kde je to jiný aby se podle toho musely vytvořit parametry)
                //   airDataLeaderboardRepository.saveAll(airDataList.get().stream().map(it -> new AirDataLeaderboard(it, it.)))
            }
        });
    }


    @Contract(" -> new")
    public @NotNull Optional<List<AirDataLeaderboard>> getAllDataFromLeaderboard() {
        return airDataLeaderboardRepository.findAll().isEmpty() ? Optional.empty() :
                Optional.of(airDataLeaderboardRepository.findAll());
    }
}
