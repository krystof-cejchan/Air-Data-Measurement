package cz.krystofcejchan.air_quality_measurement.notifications.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * sends an email
     *
     * @param details {@link EmailDetails} with all required information
     * @return HttpStatus that corresponds with email sending status
     */
    @Override
    public HttpStatus sendSimpleMail(@Nullable JavaMailSender javaMailSender, @NotNull EmailDetails @NotNull ... details) {
        if (this.javaMailSender == null && javaMailSender != null)
            this.javaMailSender = javaMailSender;
        try {
            var listOfEmails = new ArrayList<MimeMessage>();
            for (EmailDetails emailDetail : details) {
                assert this.javaMailSender != null;
                MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper
                        = new MimeMessageHelper(mimeMessage, true);
                String sender = "upocasi.notifications.noreply@gmail.com";
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(emailDetail.getRecipient());
                mimeMessageHelper.setText(emailDetail.getMsgBody(), true);
                mimeMessageHelper.setSubject(
                        emailDetail.getSubject());
                listOfEmails.add(mimeMessage);
            }

            assert this.javaMailSender != null;
            this.javaMailSender.send(listOfEmails.toArray(new MimeMessage[0]));

            return HttpStatus.ACCEPTED;
        } catch (MessagingException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
