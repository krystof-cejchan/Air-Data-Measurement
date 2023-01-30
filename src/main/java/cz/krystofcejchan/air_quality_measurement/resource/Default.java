package cz.krystofcejchan.air_quality_measurement.resource;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public final class Default {
    @Contract(pure = true)
    @RequestMapping("/")
    public @NotNull String greetings() {
        return "Hello, World!";
    }
}
