package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.EventDTO;
import br.edu.ifgoiano.Empreventos.dto.EventResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Event;
import br.edu.ifgoiano.Empreventos.model.EventStatus;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    private final Logger logger = Logger.getLogger(EventService.class.getName());

    public List<EventResponseDTO> findAll() {
        logger.info("Encontrando todos os eventos!");
        List<Event> events = eventRepository.findAll();
        return DataMapper.parseListObjects(events, EventResponseDTO.class);
    }

    public EventResponseDTO findById(Integer id) {
        logger.info("Encontrando um Evento pelo ID!");
        var event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        return DataMapper.parseObject(event, EventResponseDTO.class);
    }

    public EventDTO create(EventDTO eventDTO) {
        logger.info("Criando um Evento!");
        var eventEntity = DataMapper.parseObject(eventDTO, Event.class);
        eventEntity.setStatus(EventStatus.DRAFT);
        var savedEvent = eventRepository.save(eventEntity);
        return DataMapper.parseObject(savedEvent, EventDTO.class);
    }

    public EventDTO update(Integer id, EventDTO eventDTO) {
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

    public void delete(Integer id) {
        logger.info("Excluindo evento");
        var event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        event.setDeletedAt(java.time.LocalDateTime.now());
        eventRepository.save(event);
    }
}