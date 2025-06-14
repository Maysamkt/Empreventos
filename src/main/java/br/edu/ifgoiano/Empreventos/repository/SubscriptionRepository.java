package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    // Encontra todas as inscrições para um determinado evento
    List<Subscription> findByEventId(Integer eventId);

    // Encontra todas as inscrições de um determinado participante (ouvinte)
    List<Subscription> findByListenerId(Integer listenerId);
}