package cz.krystofcejchan.air_quality_measurement.notifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationReceiver, Long> {
    Optional<NotificationReceiver> findByIdAndRndHash(Long id, String hash);
    Optional<NotificationReceiver> findByEmailAddress(String emailAddress);
}
