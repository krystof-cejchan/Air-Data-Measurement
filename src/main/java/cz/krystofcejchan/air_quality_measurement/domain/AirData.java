package cz.krystofcejchan.air_quality_measurement.domain;

import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "airdata")
public class AirData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    private String arduinoTime;
    private LocalDateTime receivedDataDateTime;
    private BigDecimal airQuality;
    @Enumerated(EnumType.STRING)
    private Location location;
    private BigDecimal temperature;
    private BigDecimal humidity;
    @Column(nullable = false, updatable = false)
    private String rndHash;
    @Column(columnDefinition = "integer default '0'", insertable = false, nullable = false)
    private Integer reportedN;
    @Column(columnDefinition = "boolean default '0'", insertable = false, nullable = false)
    private Boolean invalidData;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private LocationData locationId;


    @Contract(pure = true)
    public AirData() {
    }

    @Contract(pure = true)
    public AirData(byte auto) {
        this(-1L, null, null, null, null, null, null, null, null, null);
    }

    @Contract(pure = true)
    public AirData(Long id, String arduinoTime, LocalDateTime receivedDataDateTime, BigDecimal airQuality,
                   Location location, BigDecimal temperature, BigDecimal humidity, String rndHash,
                   Integer reportedN, Boolean invalidData) {
        this.id = id;
        this.arduinoTime = arduinoTime;
        this.receivedDataDateTime = receivedDataDateTime;
        this.airQuality = airQuality;
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rndHash = rndHash;
        this.reportedN = reportedN;
        this.invalidData = invalidData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getReportedN() {
        return reportedN;
    }

    public void setReportedN(Integer reportedN) {
        this.reportedN = reportedN;
    }

    public Boolean getInvalidData() {
        return invalidData;
    }

    public void setInvalidData(Boolean invalidData) {
        this.invalidData = invalidData;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
