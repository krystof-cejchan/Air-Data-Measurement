package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.scheduled_tasks.ScheduledTaskRunnable;
import cz.krystofcejchan.air_quality_measurement.utilities.ZonedDateUtils;
import org.jetbrains.annotations.Contract;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

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
        final var sendingTime = LocalTime.of(5, 0, 0, 0);
        final var plusDays = LocalTime.now().isAfter(sendingTime) ? 1 : 0;
        scheduledExecutorService.scheduleAtFixedRate(sendEmailsAndRemoveInactiveAccounts,
                LocalDateTime.now(ZonedDateUtils.getPragueZoneId())
                        .until(LocalDateTime.of(LocalDate.now(ZonedDateUtils.getPragueZoneId()).plusDays(plusDays),
                                sendingTime), ChronoUnit.MINUTES),//10L,
                Period.days(1).getMinutes(),
                TimeUnit.MINUTES);

    }
}
