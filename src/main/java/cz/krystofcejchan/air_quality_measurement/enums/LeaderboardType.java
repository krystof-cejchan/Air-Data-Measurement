package cz.krystofcejchan.air_quality_measurement.enums;

import java.util.Arrays;
import java.util.List;

/**
 * The enum Leaderboard type.
 */
public enum LeaderboardType {
    /**
     * Highest temp leaderboard type.
     */
    HIGHEST_TEMP,
    /**
     * Lowest temp leaderboard type.
     */
    LOWEST_TEMP,
    /**
     * Highest airQuality leaderboard type.
     */
    HIGHEST_AIRQ,
    /**
     * Lowest airQuality leaderboard type.
     */
    LOWEST_AIRQ,
    /**
     * Highest hum leaderboard type.
     */
    HIGHEST_HUM,
    /**
     * Lowest hum leaderboard type.
     */
    LOWEST_HUM;

    public static List<LeaderboardType> toList() {
        return Arrays.stream(LeaderboardType.values()).toList();
    }

    public static List<LeaderboardType> toListWithout(LeaderboardType exclude) {
        return Arrays.stream(values()).filter(it -> !it.equals(exclude)).toList();
    }
}
