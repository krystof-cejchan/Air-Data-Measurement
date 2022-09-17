package cz.krystofcejchan.air_quality_measurement.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;


class AirDataTest {

    @Test
    void dateTime() {
        {
            System.out.println(ZoneId.getAvailableZoneIds());
            System.out.println(LocalDateTime.now(ZoneId.of("Europe/Prague")));
        }

    }
}