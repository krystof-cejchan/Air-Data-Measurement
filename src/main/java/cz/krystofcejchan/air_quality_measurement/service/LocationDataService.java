package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationDataService {
    @Autowired
    LocationDataRepository locationDataRepository;

    @Contract(pure = true)
    @Autowired
    public LocationDataService() {
    }


}
