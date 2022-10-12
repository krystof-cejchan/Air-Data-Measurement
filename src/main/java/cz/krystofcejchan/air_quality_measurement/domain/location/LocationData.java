package cz.krystofcejchan.air_quality_measurement.domain.location;

import cz.krystofcejchan.air_quality_measurement.domain.AirData;
import cz.krystofcejchan.air_quality_measurement.enums.Location;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "locations")
public class LocationData implements Serializable {
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
    /*@OneToOne(mappedBy = "locationId",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="id")
    private AirData airdata;*/

    public LocationData() {
    }

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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOutOfService() {
        return outOfService;
    }

    public void setOutOfService(Boolean outOfService) {
        this.outOfService = outOfService;
    }

    public cz.krystofcejchan.air_quality_measurement.enums.Location getLocation() {
        return location;
    }

    public void setLocation(cz.krystofcejchan.air_quality_measurement.enums.Location location) {
        this.location = location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public BigDecimal getMetersAboveTheGroundApprox() {
        return metersAboveTheGroundApprox;
    }

    public void setMetersAboveTheGroundApprox(BigDecimal metersAboveTheGroundApprox) {
        this.metersAboveTheGroundApprox = metersAboveTheGroundApprox;
    }

    public String getRoomIdentifier() {
        return roomIdentifier;
    }

    public void setRoomIdentifier(String roomIdentifier) {
        this.roomIdentifier = roomIdentifier;
    }
}
