package cz.krystofcejchan.air_quality_measurement.notifications.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * sends an email
     *
     * @param details {@link EmailDetails} with all required information
     * @return HttpStatus that corresponds with email sending status
     */
    public HttpStatus sendSimpleMail(@NotNull EmailDetails details) {

        try {
            var listOfEmails = new ArrayList<MimeMessage>();
            for (String k : details.getRecipientToText().keySet()) {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper
                        = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setFrom("upocasi.notifications.noreply@gmail.com");
                mimeMessageHelper.setTo(k);
                mimeMessageHelper.setText(details.getRecipientToText().get(k), true);
                mimeMessageHelper.setSubject(
                        details.getSubject());
                listOfEmails.add(mimeMessage);
            }

            javaMailSender.send(listOfEmails.toArray(new MimeMessage[0]));

            return HttpStatus.ACCEPTED;
        } catch (MessagingException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
