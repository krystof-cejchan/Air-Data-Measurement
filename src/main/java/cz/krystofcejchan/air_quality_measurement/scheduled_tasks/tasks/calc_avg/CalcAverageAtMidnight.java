package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.calc_avg;

import cz.krystofcejchan.air_quality_measurement.repository.AirDataAverageOfDayRepository;
import cz.krystofcejchan.air_quality_measurement.repository.AirDataRepository;
import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.air_quality_measurement.service.AirDataAverageOfDayService;
import cz.krystofcejchan.air_quality_measurement.utilities.ZonedDateUtils;
import org.jetbrains.annotations.Contract;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public record CalcAverageAtMidnight(
        AirDataAverageOfDayRepository avgRepository,
        AirDataRepository airDataRepository,
        LocationDataRepository locationDataRepository) implements ScheduledTaskRunnable {

    @Contract(pure = true)
    public CalcAverageAtMidnight {
    }


    @Override
    public void runScheduledTask() {
        //new ScheduledExecutorService with one assigned thread
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        final AirDataAverageOfDayService service = new AirDataAverageOfDayService(
                avgRepository, airDataRepository, locationDataRepository);


        //what happens when service is triggered
        Runnable calcAverageDataAndAddToDatabase = () -> {

            ResponseEntity<?> calculatedAvgWithResponse = CalcAvgFactory.calc(service, null);


            System.out.println("Avg calculation:\t" + (calculatedAvgWithResponse.getStatusCode().is2xxSuccessful() ? "SUCCESS"
                    : "FAIL => NOT NEEDED / COULD NOT CONNECT TO JPA"));

        };

        //first trigger at 00:00, then wait one day to trigger again
        scheduledExecutorService.scheduleAtFixedRate(calcAverageDataAndAddToDatabase,
                LocalDateTime.now(ZonedDateUtils.getPragueZoneId())
                        .until(LocalDateTime.of(LocalDate.now(ZonedDateUtils.getPragueZoneId())
                                        .plusDays(1), LocalTime.MIN.plusSeconds(2)),
                                // LocalDateTime.of(LocalDate.now(), LocalTime.now()/*.plusSeconds(15)*/),
                                ChronoUnit.SECONDS),
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }
}
