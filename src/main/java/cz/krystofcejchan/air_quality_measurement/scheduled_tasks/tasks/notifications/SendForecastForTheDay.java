package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.air_quality_measurement.utilities.ZonedDateUtils;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public record SendForecastForTheDay(
        NotificationsRepository notificationsRepository,
        JavaMailSender javaMailSender) implements ScheduledTaskRunnable {
    @Autowired
    @Contract(pure = true)
    public SendForecastForTheDay {
    }

    @Override
    public void runScheduledTask() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        Runnable sendEmailsAndRemoveInactiveAccounts = () ->
                new SendForecastManager(notificationsRepository, javaMailSender).sendEmailsAndDeleteInactiveAccounts();


        scheduledExecutorService.scheduleAtFixedRate(sendEmailsAndRemoveInactiveAccounts,
                LocalDateTime.now(ZonedDateUtils.getPragueZoneId())
                        .until(LocalDateTime.of(LocalDate.now(ZonedDateUtils.getPragueZoneId()).plusDays(1),
                                LocalTime.of(5, 0)), ChronoUnit.SECONDS),//20L,
                Duration.ofDays(1).getSeconds(),
                TimeUnit.SECONDS);

    }
}
