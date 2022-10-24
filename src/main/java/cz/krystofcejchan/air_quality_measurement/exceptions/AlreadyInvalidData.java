package cz.krystofcejchan.air_quality_measurement.exceptions;

/**
 * The type Already invalid data.
 */
public class AlreadyInvalidData extends RuntimeException {
    /**
     * Instantiates a new Already invalid data.
     */
    public AlreadyInvalidData() {
    }

    /**
     * Instantiates a new Already invalid data.
     *
     * @param msg the msg
     */
    public AlreadyInvalidData(String msg) {
        super(msg);
    }
}
