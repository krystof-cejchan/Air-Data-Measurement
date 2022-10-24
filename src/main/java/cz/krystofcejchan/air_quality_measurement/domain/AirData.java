package cz.krystofcejchan.air_quality_measurement.domain;

import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Air data.
 */
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


    /**
     * Instantiates a new Air data.
     */
    @Contract(pure = true)
    public AirData() {
    }

    /**
     * Instantiates a new Air data.
     *
     * @param auto the auto
     */
    @Contract(pure = true)
    public AirData(byte auto) {
        this(-1L, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Instantiates a new Air data.
     *
     * @param id                   the id
     * @param arduinoTime          the arduino time
     * @param receivedDataDateTime the received data date time
     * @param airQuality           the air quality
     * @param location             the location
     * @param temperature          the temperature
     * @param humidity             the humidity
     * @param rndHash              the rnd hash
     * @param reportedN            the reported n
     * @param invalidData          the invalid data
     */
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
     * Gets arduino time.
     *
     * @return the arduino time
     */
    public String getArduinoTime() {
        return arduinoTime;
    }

    /**
     * Sets arduino time.
     *
     * @param arduinoTime the arduino time
     */
    public void setArduinoTime(String arduinoTime) {
        this.arduinoTime = arduinoTime;
    }

    /**
     * Gets received data date time.
     *
     * @return the received data date time
     */
    public LocalDateTime getReceivedDataDateTime() {
        return receivedDataDateTime;
    }

    /**
     * Sets received data date time.
     *
     * @param receivedDataDateTime the received data date time
     */
    public void setReceivedDataDateTime(LocalDateTime receivedDataDateTime) {
        this.receivedDataDateTime = receivedDataDateTime;
    }

    /**
     * Gets air quality.
     *
     * @return the air quality
     */
    public BigDecimal getAirQuality() {
        return airQuality;
    }

    /**
     * Sets air quality.
     *
     * @param airQuality the air quality
     */
    public void setAirQuality(BigDecimal airQuality) {
        this.airQuality = airQuality;
    }

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    public BigDecimal getTemperature() {
        return temperature;
    }

    /**
     * Sets temperature.
     *
     * @param temperature the temperature
     */
    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets humidity.
     *
     * @return the humidity
     */
    public BigDecimal getHumidity() {
        return humidity;
    }

    /**
     * Sets humidity.
     *
     * @param humidity the humidity
     */
    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    /**
     * Gets rnd hash.
     *
     * @return the rnd hash
     */
    public String getRndHash() {
        return rndHash;
    }

    /**
     * Sets rnd hash.
     *
     * @param rndHash the rnd hash
     */
    public void setRndHash(String rndHash) {
        this.rndHash = rndHash;
    }

    /**
     * Gets reported n.
     *
     * @return the reported n
     */
    public Integer getReportedN() {
        return reportedN;
    }

    /**
     * Sets reported n.
     *
     * @param reportedN the reported n
     */
    public void setReportedN(Integer reportedN) {
        this.reportedN = reportedN;
    }

    /**
     * Gets invalid data.
     *
     * @return the invalid data
     */
    public Boolean getInvalidData() {
        return invalidData;
    }

    /**
     * Sets invalid data.
     *
     * @param invalidData the invalid data
     */
    public void setInvalidData(Boolean invalidData) {
        this.invalidData = invalidData;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
