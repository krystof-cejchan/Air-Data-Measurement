package cz.krystofcejchan.air_quality_measurement.scheduled_tasks.tasks.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.NotificationReceiver;
import cz.krystofcejchan.air_quality_measurement.notifications.NotificationsRepository;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailDetails;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailService;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailServiceImpl;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailTemplates;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendForecastManager {
    private final NotificationsRepository notificationsRepository;
    private final EmailService emailService = new EmailServiceImpl();
    private final JavaMailSender javaMailSender;

    public SendForecastManager(NotificationsRepository notificationsRepository, JavaMailSender javaMailSender) {
        this.notificationsRepository = notificationsRepository;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailsAndDeleteInactiveAccounts() {
        var allNotificationReceivers = notificationsRepository.findAll();
        var inactive = allNotificationReceivers.stream()
                .filter(receiver -> !receiver.getConfirmed())
                .map(NotificationReceiver::getId).toList();
        notificationsRepository.deleteAllById(inactive);
        emailService.sendSimpleMail(this.javaMailSender, allNotificationReceivers.stream()
                .filter(NotificationReceiver::getConfirmed)
                .map(it -> new EmailDetails(it, EmailTemplates.WEATHER_FORECAST))
                .toList().toArray(new EmailDetails[0]));
    }
}