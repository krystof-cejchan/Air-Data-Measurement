package cz.krystofcejchan.air_quality_measurement.domain;

import cz.krystofcejchan.air_quality_measurement.domain.location.LocationData;
import cz.krystofcejchan.air_quality_measurement.enums.LeaderboardType;
import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;

/**
 * The type Air data leaderboard.
 */
@Entity
public class AirDataLeaderboard implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(referencedColumnName = "id")
    private AirData airDataId;
    @Enumerated(EnumType.STRING)
    private LeaderboardType leaderboardType;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private LocationData locationId;
    private Integer position;

    /**
     * Instantiates a new Air data leaderboard.
     */
    @Contract(pure = true)
    public AirDataLeaderboard() {
    }

    @Contract(pure = true)
    public AirDataLeaderboard(Long id, AirData airDataId, LeaderboardType leaderboardType, LocationData locationId,
                              Integer position) {
        this.id = id;
        this.airDataId = airDataId;
        this.leaderboardType = leaderboardType;
        this.locationId = locationId;
        this.position = position;
    }

    @Contract(pure = true)
    public AirDataLeaderboard(AirData airDataId, LeaderboardType leaderboardType, LocationData locationId,
                              Integer position) {
        this.id = -2L;
        this.airDataId = airDataId;
        this.leaderboardType = leaderboardType;
        this.locationId = locationId;
        this.position = position;
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
     * Gets air data id.
     *
     * @return the air data id
     */
    public AirData getAirDataId() {
        return airDataId;
    }

    /**
     * Sets air data id.
     *
     * @param airDataId the air data id
     */
    public void setAirDataId(AirData airDataId) {
        this.airDataId = airDataId;
    }

    /**
     * Gets leaderboard type.
     *
     * @return the leaderboard type
     */
    public LeaderboardType getLeaderboardType() {
        return leaderboardType;
    }

    /**
     * Sets leaderboard type.
     *
     * @param leaderboardType the leaderboard type
     */
    public void setLeaderboardType(LeaderboardType leaderboardType) {
        this.leaderboardType = leaderboardType;
    }

    public LocationData getLocationId() {
        return locationId;
    }

    public void setLocationId(LocationData locationId) {
        this.locationId = locationId;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "AirDataLeaderboard{\n" +
                "id=" + id +
                "\n, airDataId=" + airDataId +
                "\n, leaderboardType=" + leaderboardType +
                "\n, locationId=" + locationId +
                "\n, position=" + position +
                '}';
    }
}
