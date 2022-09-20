package cz.krystofcejchan.air_quality_measurement.domain.other_objects;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;

import java.io.Serializable;
import java.math.BigDecimal;

public class AirDataAverage extends AirData implements Serializable {
    private BigDecimal avgTemperature, avgHumidity, avgAirQuality;
//unfinished
}
