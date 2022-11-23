package cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table;


import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class LeaderboardTable {
    /**
     * firstly, this method deletes all the data that already exist in the leaderboard table,
     * yet they do not exist in the newly generated data stored in newLeaderboardDataMap.
     * Secondly, all the data that are newly generated but cannot be found in the leaderboard table in the database, shall be saved there
     *
     * @param existingAirData       List of data that already exist in the leaderboard table - {@link AirDataLeaderboardRepository}.findAll()
     * @param newLeaderboardDataMap freshly generated data from AirData table and saved into a map which takes {@link LeaderBoardKey} as a key
     *                              and {@link List} of {@link AirData} as a value
     */
    public static void saveChangedDataAndDeleteOldData(AirDataLeaderboardRepository airDataLeaderboardRepository,
                                                       @NotNull List<AirDataLeaderboard> existingAirData,
                                                       @NotNull Map<LeaderBoardKey, List<AirData>> newLeaderboardDataMap) {

        List<AirDataLeaderboard> airDataLeaderboardToBeDeleted = new ArrayList<>();
        List<AirDataLeaderboard> airDataLeaderboardToBeInserted = new ArrayList<>();

        newLeaderboardDataMap.forEach((key, value) -> value.forEach(airData -> {

            existingAirData.forEach(airDataLeaderboard ->
            {
                if (Objects.equals(airDataLeaderboard.getAirDataId().getId(), airData.getId())) {
                    airDataLeaderboardToBeDeleted.add(airDataLeaderboard);
                }
            });


            if (existingAirData.stream().noneMatch(data -> Objects.equals(data.getId(), airData.getId()))) {
                airDataLeaderboardToBeInserted.add(new AirDataLeaderboard(airData,
                        key.getLeaderboardType(), airData.getLocation(), value.indexOf(airData) + 1));
            }
        }));
        airDataLeaderboardRepository.deleteAll(airDataLeaderboardToBeDeleted);
        airDataLeaderboardRepository.saveAll(airDataLeaderboardToBeInserted);

    }

    /**
     * Prepares data from AirData database to be inserted into the leaderboard database
     *
     * @return Map {@link LeaderBoardKey} to {@link List} of {@link AirData}
     */
    @Contract(pure = true)
    public static @NotNull Map<LeaderBoardKey, List<AirData>> getFreshDataForLeaderboard(AirDataRepository airDataRepo) {
        Map<LeaderBoardKey, List<AirData>> map = new HashMap<>();


        for (LeaderboardType leaderboardType : LeaderboardType.values()) {
            switch (leaderboardType) {
                case HIGHEST_HUM -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3HumidityByOrderByHumidityDesc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case LOWEST_HUM -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3HumidityByOrderByHumidityAsc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case HIGHEST_AIRQ -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3AirQualityByOrderByAirQualityDesc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case LOWEST_AIRQ -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3AirQualityByOrderByAirQualityAsc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case HIGHEST_TEMP -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3TemperatureByOrderByTemperatureDesc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
                case LOWEST_TEMP -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3TemperatureDistinctByOrderByTemperatureAsc()
                                .orElse(Collections.singletonList(new AirData((byte) -1))));
            }

        }

        map.values().removeIf(values -> values.isEmpty() || values.stream().anyMatch(value -> value.getId() < 1));
        Stream.iterate('*', i -> i)
                .limit(2000)
                .forEach(System.out::print);
        map.forEach((k, v) -> System.out.println(k + " → " + v));
        return map;
    }
}
