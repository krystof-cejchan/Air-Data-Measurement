package cz.krystofcejchan.air_quality_measurement.enums;

import java.util.Arrays;
import java.util.List;

/**
 * The enum Location.
 */
public enum Location {
//when editing LocationData enum you need to also edit code in the front-end
    //for instance latest-data.component.ts + enum_locations.ts
    /**
     * Faculty of Education.
     */
    PdF,
    /**
     * Faculty of Science.
     */
    PrF,
    /**
     * Faculty of Law
     */
    PF,
    /**
     * Faculty of Medicine and Dentistry.
     */
    LF,
    /**
     * Faculty of Health Sciences.
     */
    FZV,
    /**
     * Faculty of Physical Culture.
     */
    FTK,
    /**
     * Faculty of Philosophy.
     */
    FF,
    /**
     * Sts Cyril and Methodius Faculty of Theology.
     */
    CMTF,
    /**
     * Other location.
     */
    OTHER;

    /**
     * String to Location
     *
     * @param location the location
     * @return the location
     */
    public static Location of(String location) {
        return Location.valueOf(location);
    }

    /**
     * streams an array to a list
     *
     * @return the list
     */
    public static List<Location> toList() {
        return Arrays.stream(Location.values()).toList();
    }
}
