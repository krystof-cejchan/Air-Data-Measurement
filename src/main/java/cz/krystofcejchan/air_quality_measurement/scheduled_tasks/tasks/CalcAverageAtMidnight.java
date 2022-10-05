package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks;

import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CalcAverageAtMidnight implements ScheduledTaskRunnable {
    @Override
    public void runScheduledTask() {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime triggerTime = LocalDateTime.now()
                .withHour(0) //midnight
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        final ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable calcAverageDataAndAddToDatabase = () -> {
            //add to avg database
            System.out.println('?');
        };

        ses.scheduleAtFixedRate(calcAverageDataAndAddToDatabase,
                now.until(triggerTime, ChronoUnit.MILLIS),
                TimeUnit.DAYS.toMillis(1),
                TimeUnit.MILLISECONDS);
    }
}
