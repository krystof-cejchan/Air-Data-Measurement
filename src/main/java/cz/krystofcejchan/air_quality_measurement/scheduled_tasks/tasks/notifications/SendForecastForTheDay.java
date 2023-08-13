package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailDetails;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendForecastForTheDay {
    @Autowired
    private NotificationsRepository notificationsRepository;
    @Autowired
    private EmailServiceImpl emailService;

    @Async
    @Scheduled(cron = "0 0 4 * * ?", zone = "Europe/Prague")
    public void scheduledEmailDelivery() {
        try {
            new SendForecastManager(notificationsRepository, emailService).sendEmailsAndDeleteInactiveAccounts();
        } catch (Exception e) {
            EmailDetails failEmailDetails = new EmailDetails(e.getMessage() + '\n' + e.getCause().toString(),
                    "Email failed to be sent.", System.getenv("DEF_EMAIL"));
            emailService.sendSimpleMail(failEmailDetails);
        }
    }
}
