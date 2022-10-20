package cz.krystofcejchan.air_quality_measurement.domain.nondatabase_objects;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Map;

public record LeaderboardData(
        Map<Map<Location, LeaderboardType>, List<AirData>> allData) {
    @Contract(pure = true)
    public LeaderboardData {
    }

}
