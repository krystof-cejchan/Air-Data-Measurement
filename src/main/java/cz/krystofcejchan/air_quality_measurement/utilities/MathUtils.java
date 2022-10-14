package cz.krystofcejchan.air_quality_measurement.utilities;

import org.jetbrains.annotations.Contract;

public class MathUtils {
    @Contract(pure = true)
    public static <T extends Number> boolean isInBetween(T number, T start, T end, boolean include) {
        if (include) {
            return number.floatValue() >= start.floatValue() && number.floatValue() <= end.floatValue();

        } else {
            return number.floatValue() > start.floatValue() && number.floatValue() < end.floatValue();
        }
    }
}
