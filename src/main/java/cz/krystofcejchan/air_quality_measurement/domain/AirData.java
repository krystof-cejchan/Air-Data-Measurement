package cz.krystofcejchan.air_quality_measurement.domain;

import cz.krystofcejchan.air_quality_measurement.enums.Location;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class AirData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Location location;
    private String arduinoTime;
    private LocalDateTime receivedDataDateTime;
    private BigDecimal airQuality;
    private BigDecimal temperature;
    private BigDecimal humidity;
    @Column(nullable = false, updatable = false)
    private String rndHash;

    @Contract(pure = true)
    public AirData() {
    }

    @Contract(pure = true)
    public AirData(Long id, String location, String arduinoTime, BigDecimal airQuality, BigDecimal temperature, BigDecimal humidity, String rndHash) {
        this.id = id;
        this.location = Location.of(location);
        this.arduinoTime = arduinoTime;
        this.airQuality = airQuality;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rndHash = rndHash;
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

    public void setLocation(String location) {
        this.location = Location.of(location);
    }

    public String getArduinoTime() {
        return arduinoTime;
    }

    public void setArduinoTime(String arduinoTime) {
        this.arduinoTime = arduinoTime;
    }

    public LocalDateTime getReceivedDataDateTime() {
        return receivedDataDateTime;
    }

    public void setReceivedDataDateTime(LocalDateTime receivedDataDateTime) {
        this.receivedDataDateTime = receivedDataDateTime;
    }

    public BigDecimal getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(BigDecimal airQuality) {
        this.airQuality = airQuality;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public String getRndHash() {
        return rndHash;
    }

    public void setRndHash(String rndHash) {
        this.rndHash = rndHash;
    }
}
