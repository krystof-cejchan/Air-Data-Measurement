package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public record SendForecastForTheDay(
        NotificationsRepository notificationsRepository,
        EmailServiceImpl emailService) {


    @Scheduled(cron = "0 0 5 * * ?", zone = "Europe/Prague")
    public void scheduledEmailDelivery() {
        new SendForecastManager(notificationsRepository, emailService).sendEmailsAndDeleteInactiveAccounts();
    }
}
