package cz.krystofcejchan.air_quality_measurement.domain.location;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.math.BigDecimal;

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
    private String city;
    @Column(nullable = false, unique = true)
    private String name_short;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = true)
    private BigDecimal latitude;
    @Column(nullable = true)
    private BigDecimal longitude;
    @Column(nullable = true)
    private Double metersAboveTheGroundApprox;
    @Column(nullable = false)
    private String imgUrl;

    @Contract(pure = true)
    public LocationData() {
    }

    @Contract(pure = true)
    public LocationData(Long id, Boolean outOfService, String city, String name, String name_short,
                        BigDecimal latitude, BigDecimal longitude, Double metersAboveTheGroundApprox, String imgUrl) {
        this.id = id;
        this.outOfService = outOfService;
        this.city = city;
        this.name = name;
        this.name_short = name_short;
        this.latitude = latitude;
        this.longitude = longitude;
        this.metersAboveTheGroundApprox = metersAboveTheGroundApprox;
        this.imgUrl = imgUrl;
    }

    @Contract(pure = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Contract(pure = true)
    public Boolean getOutOfService() {
        return outOfService;
    }

    public void setOutOfService(Boolean outOfService) {
        this.outOfService = outOfService;
    }

    @Contract(pure = true)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Contract(pure = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Contract(pure = true)
    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Contract(pure = true)
    public Double getMetersAboveTheGroundApprox() {
        return metersAboveTheGroundApprox;
    }

    public void setMetersAboveTheGroundApprox(Double metersAboveTheGroundApprox) {
        this.metersAboveTheGroundApprox = metersAboveTheGroundApprox;
    }

    @Contract(pure = true)
    public String getName_short() {
        return name_short;
    }

    public void setName_short(String name_short) {
        this.name_short = name_short;
    }

    @Contract(pure = true)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

