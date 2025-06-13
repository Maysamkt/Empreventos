package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.AtividadeDTO;
import br.edu.ifgoiano.Empreventos.dto.EventDTO;
import br.edu.ifgoiano.Empreventos.dto.EventResponseDTO;
import br.edu.ifgoiano.Empreventos.service.AtividadeService;
import br.edu.ifgoiano.Empreventos.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO create(@Valid @RequestBody EventDTO eventDTO) {
        return eventService.create(eventDTO);
    }

    @GetMapping
    public List<EventResponseDTO> findAll() {
        return eventService.findAll();
    }

    @GetMapping(value = "/{id}")
    public EventResponseDTO findById(@PathVariable(value = "id") Long id) {
        return eventService.findById(id);
    }

    @PutMapping(value = "/{id}")
    public EventDTO update(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        return eventService.update(id, eventDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints de Atividades ---
    @PostMapping(value = "/{eventoId}/atividades")
    @ResponseStatus(HttpStatus.CREATED)
    public AtividadeDTO addAtividade(
            @PathVariable("eventoId") Long eventoId,
            @Valid @RequestBody AtividadeDTO atividadeDTO) {
        return atividadeService.create(eventoId, atividadeDTO);
    }

    @GetMapping(value = "/{eventoId}/atividades")
    public List<AtividadeDTO> findAtividadesByEvento(@PathVariable("eventoId") Long eventoId) {
        return atividadeService.findByEventoId(eventoId);
    }
}