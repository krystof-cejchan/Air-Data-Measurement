package cz.krystofcejchan.air_quality_measurement.domain.location;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The type Location data.
 */
@Entity
@Table(name = "locations")
public final class LocationData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    @Column(name = "outofservice", columnDefinition = "boolean default 0", insertable = false, nullable = false)
    private Boolean outOfService;
    @Column(nullable = false)
    private cz.krystofcejchan.air_quality_measurement.enums.Location location;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private Integer houseNumber;
    private String roomIdentifier;
    @Column(name = "metersAboveGround")
    private BigDecimal metersAboveTheGroundApprox;
    @OneToOne(mappedBy = "locationId")
    private AirData airdata;

    /**
     * Instantiates a new Location data.
     */
    @Contract(pure = true)
    public LocationData() {
    }

    /**
     * Instantiates a new Location data.
     *
     * @param id                         the id
     * @param outOfService               the out of service
     * @param location                   the location
     * @param street                     the street
     * @param city                       the city
     * @param houseNumber                the house number
     * @param roomIdentifier             the room identifier
     * @param metersAboveTheGroundApprox the meters above the ground approx
     * @param airdata                    the airdata
     */
    @Contract(pure = true)
    public LocationData(Long id, Boolean outOfService, Location location, String street, String city, Integer houseNumber,
                        String roomIdentifier, BigDecimal metersAboveTheGroundApprox, AirData airdata) {
        this.id = id;
        this.outOfService = outOfService;
        this.location = location;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.roomIdentifier = roomIdentifier;
        this.metersAboveTheGroundApprox = metersAboveTheGroundApprox;
        this.airdata = airdata;
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
     * Gets out of service.
     *
     * @return the out of service
     */
    public Boolean getOutOfService() {
        return outOfService;
    }

    /**
     * Sets out of service.
     *
     * @param outOfService the out of service
     */
    public void setOutOfService(Boolean outOfService) {
        this.outOfService = outOfService;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public cz.krystofcejchan.air_quality_measurement.enums.Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(cz.krystofcejchan.air_quality_measurement.enums.Location location) {
        this.location = location;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets house number.
     *
     * @return the house number
     */
    public Integer getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets house number.
     *
     * @param houseNumber the house number
     */
    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Gets meters above the ground approx.
     *
     * @return the meters above the ground approx
     */
    public BigDecimal getMetersAboveTheGroundApprox() {
        return metersAboveTheGroundApprox;
    }

    /**
     * Sets meters above the ground approx.
     *
     * @param metersAboveTheGroundApprox the meters above the ground approx
     */
    public void setMetersAboveTheGroundApprox(BigDecimal metersAboveTheGroundApprox) {
        this.metersAboveTheGroundApprox = metersAboveTheGroundApprox;
    }

    /**
     * Gets room identifier.
     *
     * @return the room identifier
     */
    public String getRoomIdentifier() {
        return roomIdentifier;
    }

    /**
     * Sets room identifier.
     *
     * @param roomIdentifier the room identifier
     */
    public void setRoomIdentifier(String roomIdentifier) {
        this.roomIdentifier = roomIdentifier;
    }

    /**
     * Gets airdata.
     *
     * @return the airdata
     */
    public AirData getAirdata() {
        return airdata;
    }

    /**
     * Sets airdata.
     *
     * @param airdata the airdata
     */
    public void setAirdata(AirData airdata) {
        this.airdata = airdata;
    }
}
