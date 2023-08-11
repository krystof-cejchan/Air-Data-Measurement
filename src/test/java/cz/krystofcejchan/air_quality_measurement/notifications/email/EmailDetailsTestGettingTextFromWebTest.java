package cz.krystofcejchan.air_quality_measurement.notifications.email;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

class EmailDetailsTestGettingTextFromWebTest {

    @Test
    void getTextFromWeb() {
        final var url = "https://krystofcejchan.cz/projects/airM/weather_forecast.txt";
        try (InputStream inputStream = new URL(url).openStream()) {
            Assertions.assertNotEquals(null, IOUtils.toString(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}