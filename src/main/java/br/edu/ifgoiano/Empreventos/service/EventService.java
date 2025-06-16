package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.controller.EventController;
import br.edu.ifgoiano.Empreventos.dto.EventDTO;
import br.edu.ifgoiano.Empreventos.dto.EventResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Activity;
import br.edu.ifgoiano.Empreventos.model.ComplementaryMaterial;
import br.edu.ifgoiano.Empreventos.model.Event;
import br.edu.ifgoiano.Empreventos.model.User;
import br.edu.ifgoiano.Empreventos.repository.UserRepository;
import br.edu.ifgoiano.Empreventos.security.jwt.UserDetailsImpl;
import br.edu.ifgoiano.Empreventos.util.EventStatus;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;


    private final Logger logger = Logger.getLogger(EventService.class.getName());

    public EventResponseDTO findById(Long id) {
        logger.info("Encontrando um Evento pelo ID!");
        var eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        EventResponseDTO eventDTO = DataMapper.parseObject(eventEntity, EventResponseDTO.class);

        // Adiciona um link para a coleção de todos os eventos
        eventDTO.add(linkTo(methodOn(EventController.class).findById(id)).withSelfRel());
        eventDTO.add(linkTo(methodOn(EventController.class).findAll()).withRel("all-events"));
        eventDTO.add(linkTo(methodOn(EventController.class).findActivitiesByEvent(id)).withRel("activities"));

        return eventDTO;
    }

    public List<EventResponseDTO> findAll() {
        logger.info("Encontrando todos os eventos!");
        List<Event> events = eventRepository.findAll();
        List<EventResponseDTO> eventDTOs = DataMapper.parseListObjects(events, EventResponseDTO.class);

        // Adicionando links para cada evento na lista
        for (EventResponseDTO event : eventDTOs) {
            event.add(linkTo(methodOn(EventController.class).findById(event.getId())).withSelfRel());
            event.add(linkTo(methodOn(EventController.class).findActivitiesByEvent(event.getId())).withRel("activities"));
        }
        return eventDTOs;
    }


    public EventDTO create(EventDTO eventDTO) {
        logger.info("Criando um Evento!");

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User organizer = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new NoSuchElementException("Organizador não encontrado."));

        var eventEntity = DataMapper.parseObject(eventDTO, Event.class);
        eventEntity.setStatus(EventStatus.DRAFT);
        eventEntity.setOrganizer(organizer);
        var savedEvent = eventRepository.save(eventEntity);
        return DataMapper.parseObject(savedEvent, EventDTO.class);
    }


    public EventDTO update(Long id, EventDTO eventDTO) {
        logger.info("Atualizando um Evento!");
        var event = eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
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
        event.setRegistrationValue(eventDTO.getRegistrationValue());
        var eventEntity = eventRepository.save(event);
        return DataMapper.parseObject(eventEntity, EventDTO.class);
    }

    public void delete(Long id) {
        logger.info("Excluindo evento e suas dependências");
        var event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        event.setDeletedAt(java.time.LocalDateTime.now());
        eventRepository.save(event);

        // Propagar a exclusão para as atividades e materiais em cascata
        if (event.getActivities() != null) {
            for (Activity activity : event.getActivities()) {
                // Marca a atividade como deletada
                activity.setDeletedAt(java.time.LocalDateTime.now());

                // Marcar os materiais complementares da atividade como deletados
                if (activity.getComplementaryMaterials() != null) {
                    for (ComplementaryMaterial material : activity.getComplementaryMaterials()) {
                        material.setDeletedAt(java.time.LocalDateTime.now());
                    }
                }
            }
            eventRepository.save(event);
        }
    }
}