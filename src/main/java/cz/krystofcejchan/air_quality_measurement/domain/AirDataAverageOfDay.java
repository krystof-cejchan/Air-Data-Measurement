package cz.krystofcejchan.air_quality_measurement.domain;

import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The type Air data average of day.
 */
@Entity
public class AirDataAverageOfDay implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id", insertable = false,updatable = false)
    private LocationData location;
    private LocalDate receivedDataDate;
    private BigDecimal airQualityAvg;
    private BigDecimal temperatureAvg;
    private BigDecimal humidityAvg;

    /**
     * Instantiates a new Air data average of day.
     */
    @Contract(pure = true)
    public AirDataAverageOfDay() {
    }


    /**
     * Instantiates a new Air data average of day.
     *
     * @param id               the id
     * @param location         the location
     * @param receivedDataDate the received data date
     * @param airQualityAvg    the air quality avg
     * @param temperatureAvg   the temperature avg
     * @param humidityAvg      the humidity avg
     */
    @Contract(pure = true)
    public AirDataAverageOfDay(Long id, LocationData location, LocalDate receivedDataDate, BigDecimal airQualityAvg,
                               BigDecimal temperatureAvg, BigDecimal humidityAvg) {
        this.id = id;
        this.location = location;
        this.receivedDataDate = receivedDataDate;
        this.airQualityAvg = airQualityAvg;
        this.temperatureAvg = temperatureAvg;
        this.humidityAvg = humidityAvg;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public LocationData getLocation() {
        return location;
    }


    public void setLocation(LocationData location) {
        this.location = location;
    }

    /**
     * Gets received data date.
     *
     * @return the received data date
     */
    public LocalDate getReceivedDataDate() {
        return receivedDataDate;
    }

    /**
     * Sets received data date.
     *
     * @param receivedDataDate the received data date
     */
    public void setReceivedDataDate(LocalDate receivedDataDate) {
        this.receivedDataDate = receivedDataDate;
    }

    /**
     * Gets air quality avg.
     *
     * @return the air quality avg
     */
    public BigDecimal getAirQualityAvg() {
        return airQualityAvg;
    }

    /**
     * Sets air quality avg.
     *
     * @param airQualityAvg the air quality avg
     */
    public void setAirQualityAvg(BigDecimal airQualityAvg) {
        this.airQualityAvg = airQualityAvg;
    }

    /**
     * Gets temperature avg.
     *
     * @return the temperature avg
     */
    public BigDecimal getTemperatureAvg() {
        return temperatureAvg;
    }

    /**
     * Sets temperature avg.
     *
     * @param temperatureAvg the temperature avg
     */
    public void setTemperatureAvg(BigDecimal temperatureAvg) {
        this.temperatureAvg = temperatureAvg;
    }

    /**
     * Gets humidity avg.
     *
     * @return the humidity avg
     */
    public BigDecimal getHumidityAvg() {
        return humidityAvg;
    }

    /**
     * Sets humidity avg.
     *
     * @param humidityAvg the humidity avg
     */
    public void setHumidityAvg(BigDecimal humidityAvg) {
        this.humidityAvg = humidityAvg;
    }
}
