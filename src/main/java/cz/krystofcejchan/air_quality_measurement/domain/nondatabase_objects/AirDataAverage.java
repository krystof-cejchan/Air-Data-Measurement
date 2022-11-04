package cz.krystofcejchan.air_quality_measurement.domain.nondatabase_objects;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The type Air data average.
 */
public final class AirDataAverage implements Serializable {
    private BigDecimal avgTemperature, avgHumidity, avgAirQuality;

    /**
     * Instantiates a new Air data average.
     */
    @Contract(pure = true)
    public AirDataAverage() {
    }

    /**
     * Instantiates a new Air data average.
     *
     * @param avgTemperature the avg temperature
     * @param avgHumidity    the avg humidity
     * @param avgAirQuality  the avg air quality
     */
    public AirDataAverage(BigDecimal avgTemperature, BigDecimal avgHumidity, BigDecimal avgAirQuality) {
        this.avgTemperature = avgTemperature;
        this.avgHumidity = avgHumidity;
        this.avgAirQuality = avgAirQuality;
    }


    /**
     * Gets avg temperature.
     *
     * @return the avg temperature
     */
    @Contract(pure = true)
    public BigDecimal getAvgTemperature() {
        return avgTemperature;
    }

    /**
     * Gets avg humidity.
     *
     * @return the avg humidity
     */
    @Contract(pure = true)
    public BigDecimal getAvgHumidity() {
        return avgHumidity;
    }

    /**
     * Gets avg air quality.
     *
     * @return the avg air quality
     */
    @Contract(pure = true)
    public BigDecimal getAvgAirQuality() {
        return avgAirQuality;
    }
}
