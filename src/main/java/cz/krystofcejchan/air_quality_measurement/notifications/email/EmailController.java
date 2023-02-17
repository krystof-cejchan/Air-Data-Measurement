package cz.krystofcejchan.air_quality_measurement.notifications.email;

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
    private EmailService emailService;

    @PostMapping("/sendMail")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<?> sendMail(@RequestBody() EmailDetails details) {
        return new ResponseEntity<>(emailService.sendSimpleMail(null,details));
    }

}
