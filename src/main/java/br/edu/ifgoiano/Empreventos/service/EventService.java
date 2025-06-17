package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.controller.EventController;
import br.edu.ifgoiano.Empreventos.controller.RatingController;
import br.edu.ifgoiano.Empreventos.dto.EventDTO;
import br.edu.ifgoiano.Empreventos.dto.EventResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.*;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import br.edu.ifgoiano.Empreventos.security.jwt.UserDetailsImpl;
import br.edu.ifgoiano.Empreventos.util.EventStatus;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingService ratingService;


    private final Logger logger = Logger.getLogger(EventService.class.getName());

    public EventResponseDTO findById(Long id) {
        logger.info("Encontrando um Evento pelo ID!");
        var eventEntity = eventRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        EventResponseDTO eventDTO = DataMapper.parseObject(eventEntity, EventResponseDTO.class);

        eventDTO.add(linkTo(methodOn(EventController.class).findById(id)).withSelfRel());
        eventDTO.add(linkTo(methodOn(EventController.class).findAll()).withRel("all-events"));
        eventDTO.add(linkTo(methodOn(EventController.class).findActivitiesByEvent(id)).withRel("activities"));
        eventDTO.add(linkTo(methodOn(EventController.class).findRatingsByEvent(id)).withRel("ratings"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {

            //Busca a avaliação específica deste usuário para este evento
            Optional<Rating> userRating = ratingService.findRatingByUserAndEvent(userDetails.getId(), id);

            //Se a avaliação existir (ifPresent), adiciona o link
            userRating.ifPresent(rating -> {
                eventDTO.add(linkTo(methodOn(RatingController.class)
                        .findRatingById(rating.getId()))
                        .withRel("my-rating"));
            });
        }
        return eventDTO;
    }

    public List<EventResponseDTO> findAll() {
        logger.info("Encontrando todos os eventos!");
        List<Event> events = eventRepository.findAllByDeletedAtIsNull();
        List<EventResponseDTO> eventDTOs = DataMapper.parseListObjects(events, EventResponseDTO.class);

        for (EventResponseDTO event : eventDTOs) {
            event.add(linkTo(methodOn(EventController.class).findById(event.getId())).withSelfRel());
            event.add(linkTo(methodOn(EventController.class).findActivitiesByEvent(event.getId())).withRel("activities"));
        }
        return eventDTOs;
    }

    @Transactional
    public EventDTO create(EventDTO eventDTO) {
        logger.info("Criando um Evento!");

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User organizer = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new NoSuchElementException("Organizador com ID " + userDetails.getId() + " não encontrado."));


        checkDuplicateEvent(eventDTO.getTitle(), eventDTO.getStartDate(), organizer, null);


        var eventEntity = DataMapper.parseObject(eventDTO, Event.class);
        eventEntity.setStatus(EventStatus.DRAFT);
        eventEntity.setOrganizer(organizer);
        eventEntity.setCreatedAt(LocalDateTime.now());
        eventEntity.setUpdatedAt(LocalDateTime.now());
        var savedEvent = eventRepository.save(eventEntity);
        logger.info("Evento criado com sucesso: " + savedEvent.getId());
        return DataMapper.parseObject(savedEvent, EventDTO.class);
    }


    public EventDTO update(Long id, EventDTO eventDTO) throws AccessDeniedException {
        logger.info("Atualizando um Evento com ID: " + id);
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = userDetails.getId();

        var event = eventRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));

        if (!event.getOrganizer().getId().equals(currentUserId)) {
            throw new AccessDeniedException("Acesso negado. Você não é o organizador deste evento.");
        }

        checkDuplicateEvent(eventDTO.getTitle(), eventDTO.getStartDate(), event.getOrganizer(), id);

        event.setTitle(eventDTO.getTitle());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setCapacity(eventDTO.getCapacity());
        event.setStartDate(eventDTO.getStartDate());
        event.setEndDate(eventDTO.getEndDate());
        event.setRegistrationValue(eventDTO.getRegistrationValue());

        if (eventDTO.getStatus() != null) {
            event.setStatus(EventStatus.valueOf(eventDTO.getStatus().toUpperCase()));
        }
        event.setUpdatedAt(LocalDateTime.now());

        event.setRegistrationValue(eventDTO.getRegistrationValue());
        var eventEntity = eventRepository.save(event);
        logger.info("Evento atualizado com sucesso: " + eventEntity.getId());
        return DataMapper.parseObject(eventEntity, EventDTO.class);
    }


    public void delete(Long id) throws AccessDeniedException {
        logger.info("Excluindo evento e suas dependências");
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = userDetails.getId();

        var event = eventRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));

        event.setDeletedAt(java.time.LocalDateTime.now());


        eventRepository.save(event);
        if (!event.getOrganizer().getId().equals(currentUserId)) {
            throw new AccessDeniedException("Acesso negado. Você não é o organizador deste evento.");
        }

        // Propagar a exclusão para as atividades e materiais em cascata
        if (event.getActivities() != null) {
            for (Activity activity : event.getActivities()) {
                activity.setDeletedAt(java.time.LocalDateTime.now());
                if (activity.getComplementaryMaterials() != null) {
                    for (ComplementaryMaterial material : activity.getComplementaryMaterials()) {
                        material.setDeletedAt(java.time.LocalDateTime.now());
                    }
                }
            }
            eventRepository.save(event);
        }
    }

    // --- Método auxiliar para verificar duplicidade ---

    private void checkDuplicateEvent(String title, LocalDateTime startDate, User organizer, Long excludeEventId) {
        Optional<Event> existingEvent;

        if (excludeEventId == null) {
            //Caso de criação: verifica se já existe um evento com esse título, data e organizador (não deletado)
            existingEvent = eventRepository.findByTitleAndStartDateAndOrganizerAndDeletedAtIsNull(title, startDate, organizer);
        } else {
            // Caso de atualização: verifica se já existe um evento com esse título, data e organizador,
            // que não seja o próprio evento que está sendo atualizado (e não deletado)
            existingEvent = eventRepository.findByTitleAndStartDateAndOrganizerAndIdNotAndDeletedAtIsNull(title, startDate, organizer, excludeEventId);
        }

        if (existingEvent.isPresent()) {
            throw new IllegalArgumentException("Já existe um evento com o mesmo título, data de início e organizador.");
        }
    }

    // Método auxiliar para obter o organizador de um evento
    @Transactional(readOnly = true)
    public Long getOrganizerIdByEventId(Long eventId) {
        return eventRepository.findByIdAndDeletedAtIsNull(eventId)
                .map(Event::getOrganizer)
                .map(User::getId)
                .orElseThrow(() -> new NoSuchElementException("Evento ou organizador não encontrado para o ID: " + eventId));
    }
}