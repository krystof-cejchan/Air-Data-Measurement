package cz.krystofcejchan.air_quality_measurement.notifications.email;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {
   abstract HttpStatus sendSimpleMail(@Nullable JavaMailSender javaMailSender, @NotNull EmailDetails @NotNull ... details);
}