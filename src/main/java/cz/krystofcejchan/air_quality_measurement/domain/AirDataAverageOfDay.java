package cz.krystofcejchan.air_quality_measurement.domain;

import cz.krystofcejchan.air_quality_measurement.enums.Location;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class AirDataAverageOfDay implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Location location;
    private LocalDate receivedDataDate;
    private BigDecimal airQualityAvg;
    private BigDecimal temperatureAvg;
    private BigDecimal humidityAvg;

    @Contract(pure = true)
    public AirDataAverageOfDay() {
    }


    @Contract(pure = true)
    public AirDataAverageOfDay(Long id, Location location, LocalDate receivedDataDate, BigDecimal airQualityAvg,
                               BigDecimal temperatureAvg, BigDecimal humidityAvg) {
        this.id = id;
        this.location = location;
        this.receivedDataDate = receivedDataDate;
        this.airQualityAvg = airQualityAvg;
        this.temperatureAvg = temperatureAvg;
        this.humidityAvg = humidityAvg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getReceivedDataDate() {
        return receivedDataDate;
    }

    public void setReceivedDataDate(LocalDate receivedDataDate) {
        this.receivedDataDate = receivedDataDate;
    }

    public BigDecimal getAirQualityAvg() {
        return airQualityAvg;
    }

    public void setAirQualityAvg(BigDecimal airQualityAvg) {
        this.airQualityAvg = airQualityAvg;
    }

    public BigDecimal getTemperatureAvg() {
        return temperatureAvg;
    }

    public void setTemperatureAvg(BigDecimal temperatureAvg) {
        this.temperatureAvg = temperatureAvg;
    }

    public BigDecimal getHumidityAvg() {
        return humidityAvg;
    }

    public void setHumidityAvg(BigDecimal humidityAvg) {
        this.humidityAvg = humidityAvg;
    }
}
