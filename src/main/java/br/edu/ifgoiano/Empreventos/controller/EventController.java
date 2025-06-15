package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.*;
import br.edu.ifgoiano.Empreventos.service.ActivityService;
import br.edu.ifgoiano.Empreventos.service.RatingService;
import br.edu.ifgoiano.Empreventos.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO create(@Valid @RequestBody EventDTO eventDTO) {
        return eventService.create(eventDTO);
    }

    @GetMapping
    public List<EventResponseDTO> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public EventResponseDTO findById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @PutMapping("/{id}")
    public EventDTO update(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        return eventService.update(id, eventDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Endpoints de Atividades ---
    @PostMapping("/{eventId}/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityDTO addActivity(@PathVariable Long eventId,
                                   @Valid
                                   @RequestBody ActivityDTO activityDTO) {
        activityDTO.setEventId(eventId);
        return activityService.create(activityDTO);
    }

    @GetMapping("/{eventId}/activities")
    public List<ActivityResponseDTO> findActivitiesByEvent(
            @PathVariable Long eventId) {
        return activityService.findByEventoId(eventId);
    }
}