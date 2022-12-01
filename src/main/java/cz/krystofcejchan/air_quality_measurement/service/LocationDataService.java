package cz.krystofcejchan.air_quality_measurement.service;

import cz.krystofcejchan.air_quality_measurement.repository.LocationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationDataService {
    @Autowired
    LocationDataRepository locationDataRepository;

    @Autowired
    public LocationDataService() {
    }


}
