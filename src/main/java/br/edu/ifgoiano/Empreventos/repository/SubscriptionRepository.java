package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.Event;
import br.edu.ifgoiano.Empreventos.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Método para encontrar todas as inscrições para um determinado evento
    List<Subscription> findByEvent(Event event);

    // Método para encontrar todas as inscrições para um determinado evento que não foram deletadas logicamente
    List<Subscription> findByEventAndDeletedAtIsNull(Event event);


    Optional<Subscription> findByIdAndDeletedAtIsNull(Long id);
}