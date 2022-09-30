package cz.krystofcejchan.air_quality_measurement.domain.other_objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class AirDataAverage implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

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
