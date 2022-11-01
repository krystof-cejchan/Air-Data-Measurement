package cz.krystofcejchan.air_quality_measurement.multithread;

import cz.krystofcejchan.air_quality_measurement.api.service.AirDataApiService;
import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderBoardMultiThread {
    public static void getDataFromDatabase(List<AirData> list, AirDataApiService service,
                                           LeaderboardType leaderboardType, Location location) {
        list.addAll(service.getLeaderBoardData(leaderboardType, location)
                .orElse(Collections.emptyList())
                .stream()
                .filter(airData -> airData.getId() > 0L)
                .toList());
    }

    public static void sortData(List<AirData> list, LeaderboardType leaderboardType) {
      list.sort(Comparator.comparing(AirData::getAirQuality));
    }
}
