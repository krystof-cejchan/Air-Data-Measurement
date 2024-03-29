package cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table;


import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataLeaderboardRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LeaderboardTable {
    /**
     * firstly, this method deletes all the data that already exist in the leaderboard table,
     * yet they do not exist in the newly generated data stored in newLeaderboardDataMap.
     * Secondly, all the data that are newly generated but cannot be found in the leaderboard table in the database, shall be saved there
     *
     * @param airDataLeaderboardRepository Autowired repository instance
     * @param existingAirData              List of data that already exist in the leaderboard table - {@link AirDataLeaderboardRepository}.findAll()
     * @param newLeaderboardDataMap        freshly generated data from AirData table and saved into a map which takes {@link LeaderBoardKey} as a key
     *                                     and {@link List} of {@link AirData} as a value
     */
    public static void saveChangedDataAndDeleteOldData(@NotNull AirDataLeaderboardRepository airDataLeaderboardRepository,
                                                       @NotNull List<AirDataLeaderboard> existingAirData,
                                                       @NotNull Map<LeaderBoardKey, Set<AirData>> newLeaderboardDataMap) {

        List<AirDataLeaderboard> airDataLeaderboardToBeInserted = new ArrayList<>();

        Map<LeaderboardType, List<AirDataLeaderboard>> existingAirDataLeaderboardByType = new HashMap<>();
        LeaderboardType.toList().forEach(leaderboardType ->
                existingAirDataLeaderboardByType
                        .putIfAbsent(leaderboardType,
                                existingAirData.stream().filter(data -> data.getLeaderboardType().equals(leaderboardType))
                                        .toList()));
        List<LeaderboardType> mismatchingLeaderboardTypes = new ArrayList<>();
        for (LeaderboardType leaderboardType : LeaderboardType.toList()) {
            if (existingAirDataLeaderboardByType.getOrDefault(leaderboardType, Collections.emptyList()).isEmpty()
                    || !existingAirDataLeaderboardByType
                    .getOrDefault(leaderboardType, Collections.emptyList())
                    .stream()
                    //filter was not originally part of this pipe; however the code broke down out of nowhere,
                    // hence the filter line was added to prevent throwing an exception
                    .filter(it -> it.getAirDataId() != null)
                    .allMatch(existing ->
                            newLeaderboardDataMap
                                    .getOrDefault(new LeaderBoardKey(leaderboardType), Collections.emptySet())
                                    .stream()
                                    .map(AirData::getId)
                                    .allMatch(id -> id.equals(existing.getAirDataId().getId())))) {
                mismatchingLeaderboardTypes.add(leaderboardType);
            }
        }

        List<AirDataLeaderboard> airDataLeaderboardToBeDeleted = new ArrayList<>(existingAirData
                .stream()
                .filter(it -> mismatchingLeaderboardTypes.contains(it.getLeaderboardType()))
                .toList());

        mismatchingLeaderboardTypes.forEach(leaderboardType -> {
            Set<AirData> airData = newLeaderboardDataMap.getOrDefault(new LeaderBoardKey(leaderboardType),
                    Collections.emptySet());
            if (!airData.isEmpty()) {
                airData.forEach(airData1 -> airDataLeaderboardToBeInserted.add(
                        new AirDataLeaderboard(airData1,
                                leaderboardType,
                                airData1.getLocationId(),
                                airData.stream().toList().indexOf(airData1) + 1)));
            }
        });

        airDataLeaderboardRepository.deleteAll(airDataLeaderboardToBeDeleted);
        airDataLeaderboardRepository.saveAll(airDataLeaderboardToBeInserted);
    }

    /**
     * Prepares data from AirData database to be inserted into the leaderboard database
     *
     * @return Map {@link LeaderBoardKey} to {@link List} of {@link AirData}
     */
    @Contract(pure = true)
    public static @NotNull Map<LeaderBoardKey, Set<AirData>> getFreshDataForLeaderboard(AirDataRepository airDataRepo) {
        Map<LeaderBoardKey, Set<AirData>> map = new HashMap<>();


        for (LeaderboardType leaderboardType : LeaderboardType.values()) {
            switch (leaderboardType) {
                case HIGHEST_HUM -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3HumidityByOrderByHumidityDesc()
                                .orElse(Collections.singleton(new AirData((byte) -1))));
                case LOWEST_HUM -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3HumidityByOrderByHumidityAsc()
                                .orElse(Collections.singleton(new AirData((byte) -1))));
                case HIGHEST_AIRQ -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3AirQualityByOrderByAirQualityDesc()
                                .orElse(Collections.singleton(new AirData((byte) -1))));
                case LOWEST_AIRQ -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3AirQualityByOrderByAirQualityAsc()
                                .orElse(Collections.singleton(new AirData((byte) -1))));
                case HIGHEST_TEMP -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3TemperatureByOrderByTemperatureDesc()
                                .orElse(Collections.singleton(new AirData((byte) -1))));
                case LOWEST_TEMP -> map.putIfAbsent(new LeaderBoardKey(leaderboardType),
                        airDataRepo
                                .findTop3TemperatureDistinctByOrderByTemperatureAsc()
                                .orElse(Collections.singleton(new AirData((byte) -1))));
            }

        }

        map.values().removeIf(values -> values.isEmpty() || values.stream().anyMatch(value -> value.getId() < 1));

        return map;
    }
}
