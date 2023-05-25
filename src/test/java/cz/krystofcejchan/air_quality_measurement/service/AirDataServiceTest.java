package cz.krystofcejchan.air_quality_measurement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class AirDataServiceTest {

    @Test
    void updateNumberReportedById() {
        var a = LocalDateTime.of(2023, 1, 15, 20, 0);
        var b = LocalDateTime.of(2023, 1, 15, 18, 0);
        Assertions.assertTrue(b.until(a, ChronoUnit.MINUTES) >= 2 * 60);
    }
}