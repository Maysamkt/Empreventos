package br.edu.ifgoiano.Empreventos.repository;

import br.edu.ifgoiano.Empreventos.model.Event;
import br.edu.ifgoiano.Empreventos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // Para buscar eventos ativos (não deletados logicamente)
    Optional<Event> findByIdAndDeletedAtIsNull(Long id);
    List<Event> findAllByDeletedAtIsNull();

    // Para verificar duplicidade na criação: título, data de início e organizador (e não deletado)
    Optional<Event> findByTitleAndStartDateAndOrganizerAndDeletedAtIsNull(String title, LocalDateTime startDate, User organizer);

    // Para verificar duplicidade na atualização: título, data de início, organizador E ID DIFERENTE (e não deletado)
    Optional<Event> findByTitleAndStartDateAndOrganizerAndIdNotAndDeletedAtIsNull(String title, LocalDateTime startDate, User organizer, Long id);
}