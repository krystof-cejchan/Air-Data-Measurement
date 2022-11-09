package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.domain.AirDataLeaderboard;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class AirDataLeaderboardServiceTest {

    @Test
    void updateLeaderboard() {
        Optional<List<AirDataLeaderboard>> airDataLeaderboards =
                Optional.of(List.of(new AirDataLeaderboard(2L, new AirData(13L, "",
                                LocalDateTime.MAX, BigDecimal.ONE, Location.PdF,
                                BigDecimal.TEN, BigDecimal.TEN, "", 0, false),
                                LeaderboardType.HIGHEST_AIRQ, Location.PdF, 1),
                        new AirDataLeaderboard(1L, new AirData(12L, "",
                                LocalDateTime.MAX, BigDecimal.TEN, Location.PdF,
                                BigDecimal.TEN, BigDecimal.TEN, "", 0, false),
                                LeaderboardType.HIGHEST_AIRQ, Location.PdF, 1)));
        Optional<List<AirData>> airDataList =
                Optional.of(Collections.singletonList(new AirData(12L, "",
                        LocalDateTime.MAX, BigDecimal.TEN, Location.PdF,
                        BigDecimal.TEN, BigDecimal.TEN, "", 0, false)));

        List<AirDataLeaderboard> a = airDataList.get()
                .stream()
                .map(it -> airDataLeaderboards.get()
                        .stream()
                        .map(t ->
                                new AirDataLeaderboard(it, t.getLeaderboardType(),
                                        t.getLocation(), t.getPosition())).toList())
                .flatMap(List::stream)
                .toList();

        a.forEach(it -> System.out.println(it.toString()));
        for (Field declaredField : AirData.class.getDeclaredFields()) {
            System.out.println(declaredField.getName());
        }
        Assertions.assertFalse(airDataLeaderboards
                .get()
                .stream()
                .map(AirDataLeaderboard::getAirDataId)
                .allMatch(m -> airDataList.get()
                        .stream()
                        .map(AirData::getId)
                        .allMatch(it -> it.equals(m.getId()))));

    }
}