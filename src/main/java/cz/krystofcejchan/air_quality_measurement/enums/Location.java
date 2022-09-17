package cz.krystofcejchan.air_quality_measurement.enums;

public enum Location {
    PdF, PrF, PF, LF, FZV, FTK, FF, CMTF;

    public static Location of(String location) {
        return Location.valueOf(location);
    }
}
