package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.EventDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Event;
import br.edu.ifgoiano.Empreventos.model.StatusEvento;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventDTO> findAll() {
        List<Event> events = eventRepository.findAll();
        return DataMapper.parseListObjects(events, EventDTO.class);
    }

    public EventDTO findById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(e -> DataMapper.parseObject(e, EventDTO.class)).orElse(null);
    }

    public EventDTO create(EventDTO eventDTO) {
        Event event = DataMapper.parseObject(eventDTO, Event.class);
        Event savedEvent = eventRepository.save(event);
        return DataMapper.parseObject(savedEvent, EventDTO.class);
    }

    public EventDTO update(Long id, EventDTO eventDTO) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = DataMapper.parseObject(eventDTO, Event.class);
            event.setId(id); // mantém o ID original
            Event updatedEvent = eventRepository.save(event);
            return DataMapper.parseObject(updatedEvent, EventDTO.class);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EventDTO> findByStatus(String status) {
        StatusEvento statusEvento = StatusEvento.valueOf(status);
        List<Event> events = eventRepository.findByStatus(statusEvento);
        return DataMapper.parseListObjects(events, EventDTO.class);
    }

    public List<EventDTO> findByEventoPago(boolean eventoPago) {
        List<Event> events = eventRepository.findByEventoPago(eventoPago);
        return DataMapper.parseListObjects(events, EventDTO.class);
    }

    public List<EventDTO> findByTitulo(String titulo) {
        List<Event> events = eventRepository.findByTituloContainingIgnoreCase(titulo);
        return DataMapper.parseListObjects(events, EventDTO.class);
    }
}
