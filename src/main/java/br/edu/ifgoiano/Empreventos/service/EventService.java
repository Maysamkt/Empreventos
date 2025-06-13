package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.EventDTO;
import br.edu.ifgoiano.Empreventos.dto.EventResponseDTO;
import br.edu.ifgoiano.Empreventos.exception.RestExceptionHandler;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Event;
import br.edu.ifgoiano.Empreventos.model.StatusEvento;
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
        return events.stream()
                .map(event -> DataMapper.parseObject(event, EventResponseDTO.class))
                .collect(Collectors.toList());
    }

    public EventResponseDTO findById(Long id) {
        logger.info("Encontrando um Evento pelo ID!");
        var event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        return DataMapper.parseObject(event, EventResponseDTO.class);
    }

    public EventDTO create(EventDTO eventDTO) {
        logger.info("Criando um Evento!");
        var event = DataMapper.parseObject(eventDTO, Event.class);
        var savedEvent = eventRepository.save(event);
        return DataMapper.parseObject(savedEvent, EventDTO.class);
    }

    public EventDTO update(Long id, EventDTO eventDTO) {
        logger.info("Atualizando um Evento!");
        var event = eventRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        event.setTitulo(eventDTO.getTitulo());
        event.setDescricao(eventDTO.getDescricao());
        event.setLocal(eventDTO.getLocal());
        event.setCapacidade(eventDTO.getCapacidade());
        event.setDataInicio(eventDTO.getDataInicio());
        event.setDataFim(eventDTO.getDataFim());
        if (eventDTO.getStatus() != null) {
            event.setStatus(StatusEvento.valueOf(eventDTO.getStatus()));
        }
        event.setEventoPago(eventDTO.isEventoPago());
        event.setValorInscricao(eventDTO.getValorInscricao());
        var eventEntity = eventRepository.save(event);
        return DataMapper.parseObject(eventEntity, EventDTO.class);
    }

    public void delete(Long id) {
        logger.info("Excluindo evento");
        var event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento com ID " + id + " não encontrado."));
        eventRepository.delete(event);
    }
}