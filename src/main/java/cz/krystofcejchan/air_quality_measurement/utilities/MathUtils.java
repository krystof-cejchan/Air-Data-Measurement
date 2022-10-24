package cz.krystofcejchan.air_quality_measurement.utilities;

import org.jetbrains.annotations.Contract;

/**
 * The type Math utils.
 */
public class MathUtils {
    /**
     * Is in between boolean.
     *
     * @param <T>     the type parameter
     * @param number  the number
     * @param start   the start
     * @param end     the end
     * @param include if true -> ">=" will be used, else >
     * @return the boolean
     */
    @Contract(pure = true)
    public static <T extends Number> boolean isInBetween(T number, T start, T end, boolean include) {
        if (include) {
            return number.floatValue() >= start.floatValue() && number.floatValue() <= end.floatValue();

        } else {
            return number.floatValue() > start.floatValue() && number.floatValue() < end.floatValue();
        }
    }
}
