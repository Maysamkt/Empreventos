package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.*;
import br.edu.ifgoiano.Empreventos.service.ActivityService;
import br.edu.ifgoiano.Empreventos.service.RatingService;
import br.edu.ifgoiano.Empreventos.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ORGANIZER')")
    public EventDTO create(@Valid @RequestBody EventDTO eventDTO) {
        return eventService.create(eventDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public EventDTO update(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) throws AccessDeniedException {
        return eventService.update(id, eventDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<?> delete(@PathVariable Long id) throws AccessDeniedException {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<EventResponseDTO> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public EventResponseDTO findById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @GetMapping("/{eventId}/ratings")
    public ResponseEntity<List<RatingDTO>> findRatingsByEvent(@PathVariable Long eventId) {
        List<RatingDTO> ratings = ratingService.findByEventId(eventId);
        return ResponseEntity.ok(ratings);
    }


    // --- Endpoints de Atividades ---
    @PostMapping("/{eventId}/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityDTO addActivity(@PathVariable Long eventId, @Valid @RequestBody ActivityDTO activityDTO) {
        activityDTO.setEventId(eventId);
        return activityService.create(activityDTO);
    }

    @GetMapping("/{eventId}/activities")
    public List<ActivityResponseDTO> findActivitiesByEvent(
            @PathVariable Long eventId) {
        return activityService.findByEventoId(eventId);
    }
}