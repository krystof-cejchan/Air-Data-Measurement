package cz.krystofcejchan.air_quality_measurement.domain.nondatabase_objects;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;

public class AirDataAverage implements Serializable {
    private BigDecimal avgTemperature, avgHumidity, avgAirQuality;

    @Contract(pure = true)
    public AirDataAverage() {
    }

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
