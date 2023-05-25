package cz.krystofcejchan.air_quality_measurement.domain.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;


class StaticLocationDataTest {
    @Test
    void URL() throws MalformedURLException {
        URL url = new URL("https", "krystofcejchan.cz", "/arduino_aiq_quality/assets/imgs/faculties/PrF_cover.jpg");
        Assertions.assertEquals(url.toString(), "https://krystofcejchan.cz/arduino_aiq_quality/assets/imgs/faculties/PrF_cover.jpg");
    }

}