package cz.krystofcejchan.air_quality_measurement.notifications.email;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    EmailServiceImpl emailService;

    @PostMapping("/sendMail")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<?> sendMail(@RequestBody() @NotNull EmailDetailsSimple details) {
        return new ResponseEntity<>(emailService.sendSimpleMail(details.getEmailDetails()));
    }

    private static final class EmailDetailsSimple extends EmailDetails {

        private EmailDetailsSimple(String recipient, String subject, String msgBody) {
            super(msgBody, subject, recipient);
        }

        @Contract(" -> new")
        private @NotNull EmailDetails getEmailDetails() {
            return this;
        }
    }

}
