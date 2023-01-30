package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.calc_avg;

import cz.krystofcejchan.air_quality_measurement.utilities.ZonedDateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class CalcAverageAtMidnightTest {

    @Test
    void runScheduledTask() {
       System.out.println(LocalDateTime.now(ZonedDateUtils.getPragueZoneId())
                .until(LocalDateTime.of(LocalDate.now(ZoneId.of("Europe/London"))
                        .plusDays(1), LocalTime.MIN.plusSeconds(3)), ChronoUnit.SECONDS));
    }
}