package cz.krystofcejchan.air_quality_measurement.resource;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController for Default mapping
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public final class Default implements ErrorController {
    /**
     * Default method called upon reaching empty subdirectories
     *
     * @return hello world message
     */
    @Contract(pure = true)
    @RequestMapping(value = "/",
            produces = MediaType.TEXT_PLAIN_VALUE)
    public @NotNull String greetings() {
        return "Hello, World!";
    }
/*
    @Contract(pure = true)
    @RequestMapping(value = "/error")
    public @NotNull ResponseEntity<String> error() {
        return new ResponseEntity<>("Page Not Found", HttpStatusCode.valueOf(404));
    }*/
}
