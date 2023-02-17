package cz.krystofcejchan.air_quality_measurement.notifications;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airdata/notifications")
@CrossOrigin(origins = {"https://krystofcejchan.cz", "http://localhost:4200"}, methods = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE},
        maxAge = 60, allowedHeaders = "*", exposedHeaders = "*")
public class NotificationResource {
    @Autowired
    private final NotificationService service;

    @Contract(pure = true)
    public NotificationResource(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<NotificationReceiver> addNewReceiver(@RequestHeader() String email) {
        System.out.println(email);
        if (email == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        NotificationReceiver addedReceiver = service.addNewNotificationReceiver(email);
        return new ResponseEntity<>(addedReceiver, addedReceiver == null ? HttpStatus.UNAUTHORIZED : HttpStatus.CREATED);
    }

    @PatchMapping("/confirm")
    public ResponseEntity<Boolean> confirmReceiver(@RequestHeader() Long id, @RequestHeader() String hash) {
        return new ResponseEntity<>(service.confirmReceiver(id, hash));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReceiver(@RequestHeader() Long id, @RequestHeader() @NotNull String hash,
                                            @RequestHeader(name = "password") Integer psw) {

        if (psw != id * hash.length())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(service.deleteReceiver(id, hash));

    }
}
