package cz.krystofcejchan.air_quality_measurement.notifications;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_receivers")
public class NotificationReceiver implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    @Column(nullable = false, updatable = false, name = "hash")
    private String rndHash;
    @Column(nullable = false, name = "email")
    private String emailAddress;
    @Column(columnDefinition = "boolean default '0'", nullable = false)
    private Boolean confirmed;
    @Column(nullable = true)
    private LocalDateTime subscriberSince;

    @Contract(pure = true)
    public NotificationReceiver() {
    }

    @Contract(pure = true)
    public NotificationReceiver(Long id, String rndHash, String emailAddress, Boolean confirmed, LocalDateTime subscriberSince) {
        this.id = id;
        this.rndHash = rndHash;
        this.emailAddress = emailAddress;
        this.confirmed = confirmed;
        this.subscriberSince = subscriberSince;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRndHash() {
        return rndHash;
    }

    public void setRndHash(String rndHash) {
        this.rndHash = rndHash;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public LocalDateTime getSubscriberSince() {
        return subscriberSince;
    }

    public void setSubscriberSince(LocalDateTime subscriberSince) {
        this.subscriberSince = subscriberSince;
    }
}
