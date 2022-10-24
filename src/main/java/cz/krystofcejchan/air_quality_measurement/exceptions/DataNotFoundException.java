package cz.krystofcejchan.air_quality_measurement.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * The type Data not found exception.
 */
public class DataNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Data not found exception.
     *
     * @param id the id
     */
    public DataNotFoundException(@NotNull Long id) {
        super("Data with id value of " + id + " does not exist.");
    }

    /**
     * Instantiates a new Data not found exception.
     */
    public DataNotFoundException() {
        super();
    }
}
