package cz.krystofcejchan.air_quality_measurement.notifications;

import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailDetails;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailService;
import cz.krystofcejchan.air_quality_measurement.notifications.email.EmailTemplates;
import cz.krystofcejchan.air_quality_measurement.utilities.ZonedDateUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    private final NotificationsRepository repository;

    @Autowired
    @Contract(pure = true)
    public NotificationService(NotificationsRepository repository) {
        this.repository = repository;
    }

    public @Nullable NotificationReceiver addNewNotificationReceiver(@NotNull String receiversEmail) {
        final NotificationReceiver receiver = new NotificationReceiver(-1L,
                UUID.randomUUID().toString(),
                receiversEmail,
                false,
                LocalDateTime.now(ZonedDateUtils.getPragueZoneId()));


        var optUserWithSameEmail = repository.findByEmailAddress(receiversEmail);
        if (optUserWithSameEmail.isEmpty()) {
            var newlySavedReceiver = repository.save(receiver);
            emailService.sendSimpleMail(null,new EmailDetails(newlySavedReceiver, EmailTemplates.CONFIRM));
            return newlySavedReceiver;
        } else return null;
    }

    @Contract(pure = true)
    public HttpStatus confirmReceiver(Long id, String hash) {
        Optional<NotificationReceiver> optReceiver = repository.findByIdAndRndHash(id, hash);
        if (optReceiver.isEmpty()) return HttpStatus.BAD_REQUEST;

        var receiver = optReceiver.get();
        if (receiver.getConfirmed()) return HttpStatus.CONFLICT;
        receiver.setConfirmed(true);
        repository.save(receiver);
        return HttpStatus.OK;
    }

    @Contract(pure = true)
    public HttpStatus deleteReceiver(Long id, String hash) {
        Optional<NotificationReceiver> optReceiver = repository.findByIdAndRndHash(id, hash);
        if (optReceiver.isEmpty()) return HttpStatus.CONFLICT;
        var receiver = optReceiver.get();
        emailService.sendSimpleMail(null,new EmailDetails(receiver, EmailTemplates.UNSUBSCRIBE));
        repository.deleteById(receiver.getId());
        return HttpStatus.OK;
    }
}
