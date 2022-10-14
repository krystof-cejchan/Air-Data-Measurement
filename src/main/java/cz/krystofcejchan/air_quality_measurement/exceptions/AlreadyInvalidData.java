package cz.krystofcejchan.air_quality_measurement.exceptions;

public class AlreadyInvalidData extends RuntimeException {
    public AlreadyInvalidData() {
    }

    public AlreadyInvalidData(String msg) {
        super(msg);
    }
}
