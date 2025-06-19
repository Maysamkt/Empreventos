package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

     List<Rating> findBySubscriptionId(Long subscriptionId);

     @Query("SELECT r FROM Rating r WHERE r.subscription.event.id = :eventId")
     List<Rating> findByEventId(@Param("eventId") Long eventId);

     @Query("SELECT r FROM Rating r WHERE r.subscription.listener.id = :userId AND r.subscription.event.id = :eventId")
     Optional<Rating> findByUserAndEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

}