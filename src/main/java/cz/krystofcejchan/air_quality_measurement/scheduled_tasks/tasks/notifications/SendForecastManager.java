package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationReceiver;
import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailDetails;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailServiceImpl;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailTemplates;
import org.springframework.http.HttpStatus;

public record SendForecastManager(
        NotificationsRepository notificationsRepository,
        EmailServiceImpl emailService) {

    public HttpStatus sendEmailsAndDeleteInactiveAccounts() {
        var allNotificationReceivers = notificationsRepository.findAll();
        var inactive = allNotificationReceivers.stream()
                .filter(receiver -> !receiver.getConfirmed())
                .map(NotificationReceiver::getId).toList();
        notificationsRepository.deleteAllById(inactive);
        return emailService.sendSimpleMail(new EmailDetails(EmailTemplates.WEATHER_FORECAST,
                allNotificationReceivers.stream()
                        .filter(NotificationReceiver::getConfirmed).toList().toArray(new NotificationReceiver[0])));
    }
}