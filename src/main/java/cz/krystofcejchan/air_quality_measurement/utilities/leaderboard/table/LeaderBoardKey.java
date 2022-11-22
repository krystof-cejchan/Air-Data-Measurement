package cz.krystofcejchan.air_quality_measurement.utilities.leaderboard.table;

import org.jetbrains.annotations.Contract;

public record LeaderBoardKey(cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType leaderboardType) {
    @Contract(pure = true)
    public LeaderBoardKey {
    }

    @Contract(pure = true)
    public cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType getLeaderboardType() {
        return leaderboardType;
    }
}