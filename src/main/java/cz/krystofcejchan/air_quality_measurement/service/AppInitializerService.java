package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppInitializerService {
    @Autowired
    @Contract(pure = true)
    public AppInitializerService() {
    }

    public Location[] getAllLocations() {
        return Location.toList().stream()
                .filter(location -> !location.equals(Location.OTHER))
                .toList()
                .toArray(new Location[0]);
    }

    public LeaderboardType[] getAllLeaderboardTypes() {
        return LeaderboardType.values();
    }
}
