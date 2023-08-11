package cz.krystofcejchan.air_quality_measurement.domain.location;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class StaticLocationData {
    public static java.util.List<LocationData> ALL_LOCATIONS_Pre = Collections.emptyList();

    static {
        try {
            ALL_LOCATIONS_Pre = java.util.List.of(
                    new LocationData(-1L, false, "Olomouc", "Pedagogická fakulta UPOL", "PdF",
                            null, null, null,
                            new URL("https", "krystofcejchan.cz", "/arduino_aiq_quality/assets/imgs/faculties/PdF_cover.jpg").toString()),

                    new LocationData(-1L, false, "Olomouc", "Přírodovědecká fakulta UPOL", "PrF",
                            null, null, null,
                            new URL("https", "krystofcejchan.cz", "/arduino_aiq_quality/assets/imgs/faculties/PrF_cover.jpg").toString()),

                    new LocationData(-1L, false, "Olomouc", "Právnická fakulta UPOL", "PF",
                            null, null, null,
                            new URL("https", "krystofcejchan.cz", "/arduino_aiq_quality/assets/imgs/faculties/PF_cover.jpg").toString()),

                    new LocationData(-1L, false, "Olomouc", "Lékařská fakulta UPOL", "LF",
                            null, null, null,
                            new URL("https", "krystofcejchan.cz", "/arduino_aiq_quality/assets/imgs/faculties/LF_cover.jpg").toString()),

                    new LocationData(-1L, false, "Náchod", "Home", "NÁCH",
                            null, null, null,
                            "https://krystofcejchan.cz/arduino_aiq_quality/assets/imgs/faculties/nachod.jpg")
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
