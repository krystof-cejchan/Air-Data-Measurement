package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public record SendForecastForTheDay(
        NotificationsRepository notificationsRepository,
        JavaMailSender javaMailSender) {
    @Autowired
    @Contract(pure = true)
    public SendForecastForTheDay {
    }

    @Scheduled(cron = "0 0 5 * * ?", zone = "Europe/Prague")
    public void scheduledEmailDelivery() {
        new SendForecastManager(notificationsRepository, javaMailSender).sendEmailsAndDeleteInactiveAccounts();
    }
}
