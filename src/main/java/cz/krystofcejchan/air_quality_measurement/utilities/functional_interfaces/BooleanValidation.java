package cz.krystofcejchan.air_quality_measurement.utilities.functional_interfaces;

@FunctionalInterface
public interface BooleanValidation<T, U> {
    /**
     * works as a {@link java.util.function.BiPredicate}
     *
     * @param t generic param
     * @param u generic param
     * @return boolean
     */
    boolean validPassed(T t, U u);
}