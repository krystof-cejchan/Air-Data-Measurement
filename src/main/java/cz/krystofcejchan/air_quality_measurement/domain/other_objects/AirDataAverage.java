package cz.krystofcejchan.air_quality_measurement.domain.other_objects;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class AirDataAverage extends AirData implements Serializable {
    private final BigDecimal avgTemperature, avgHumidity, avgAirQuality;

    public AirDataAverage(BigDecimal avgTemperature, BigDecimal avgHumidity, BigDecimal avgAirQuality) {
        this.avgTemperature = avgTemperature;
        this.avgHumidity = avgHumidity;
        this.avgAirQuality = avgAirQuality;
    }

    public BigDecimal getAvgTemperature() {
        return avgTemperature;
    }

    public BigDecimal getAvgHumidity() {
        return avgHumidity;
    }

    public BigDecimal getAvgAirQuality() {
        return avgAirQuality;
    }
}
