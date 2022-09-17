package cz.krystofcejchan.air_quality_measurement.exceptions;

import org.jetbrains.annotations.NotNull;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(@NotNull Long id) {
        super("Data with id value of " + id + " does not exist.");
    }
}
