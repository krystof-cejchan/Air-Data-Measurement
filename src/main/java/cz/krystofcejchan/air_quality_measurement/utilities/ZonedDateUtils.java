package cz.krystofcejchan.air_quality_measurement.utilities;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;

public class ZonedDateUtils {
    /**
     *
     * @return ZoneId of Prague
     */
    @Contract(pure = true)
    public static @NotNull ZoneId getPragueZoneId() {
        return ZoneId.of("Europe/Prague");
    }
}

