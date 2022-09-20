package cz.krystofcejchan.air_quality_measurement.enums;

import java.util.Arrays;
import java.util.List;

public enum Location {
    PdF, PrF, PF, LF, FZV, FTK, FF, CMTF, OTHER;

    public static Location of(String location) {
        return Location.valueOf(location);
    }

    public static List<Location> toList() {
        return Arrays.stream(Location.values()).toList();
    }
}
