package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailDetails;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public record SendForecastForTheDay(
        NotificationsRepository notificationsRepository,
        EmailServiceImpl emailService) {


    @Scheduled(cron = "0 0 4 * * ?", zone = "Europe/Prague")
    public void scheduledEmailDelivery() {
        try {
            new SendForecastManager(notificationsRepository, emailService).sendEmailsAndDeleteInactiveAccounts();
        } catch (Exception e) {
            var failEmailDetails = new EmailDetails(e.toString(),
                    "Email failed to be sent.", "krystofcejchan@gmail.com");
            emailService.sendSimpleMail(failEmailDetails);
        }
    }
}
