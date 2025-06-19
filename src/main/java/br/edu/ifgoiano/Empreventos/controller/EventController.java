package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.*;
import br.edu.ifgoiano.Empreventos.service.ActivityService;
import br.edu.ifgoiano.Empreventos.service.RatingService;
import br.edu.ifgoiano.Empreventos.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Eventos", description = "Operações relacionadas a gerenciamento de eventos")
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
    @Operation(summary = "Criar um novo evento", description = "Permite que um organizador crie um novo evento.")
    @SecurityRequirement(name = "bearerAuth") // Requer autenticação JWT
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do evento inválidos ou evento duplicado (título, data, organizador)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (apenas ORGANIZERs)")
    })
    public EventDTO create(@Valid @RequestBody EventDTO eventDTO) {
        return eventService.create(eventDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANIZER')")
    @Operation(summary = "Atualizar um evento existente", description = "Permite a um organizador atualizar os detalhes de um evento. O organizador deve ser o criador do evento ou um ADMIN.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "id", description = "ID do evento a ser atualizado", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do evento inválidos ou evento duplicado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (não é o organizador ou não tem permissão)"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public EventDTO update(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) throws AccessDeniedException {
        return eventService.update(id, eventDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZER') and @eventService.getOrganizerIdByEventId(#id) == authentication.principal.id)")
    @Operation(summary = "Deletar um evento", description = "Permite a um ADMIN ou ao organizador do evento deletá-lo logicamente. A exclusão é em cascata para atividades e materiais.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "id", description = "ID do evento a ser deletado", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso (No Content)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable Long id) throws AccessDeniedException {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar todos os eventos", description = "Lista todos os eventos ativos. Atualmente, apenas ADMINs podem ver todos os eventos.")
    @SecurityRequirement(name = "bearerAuth") // Se for @PreAuthorize, provavelmente requer token
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (se não for ADMIN)")
    })
    public List<EventResponseDTO> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Buscar evento por ID", description = "Busca os detalhes de um evento específico pelo seu ID. Acesso liberado para todos os usuários autenticados.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "id", description = "ID do evento", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public EventResponseDTO findById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @GetMapping("/{eventId}/ratings")
    @Operation(summary = "Listar avaliações de um evento", description = "Lista todas as avaliações associadas a um evento específico.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "eventId", description = "ID do evento para listar avaliações", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<List<RatingDTO>> findRatingsByEvent(@PathVariable Long eventId) {
        List<RatingDTO> ratings = ratingService.findByEventId(eventId);
        return ResponseEntity.ok(ratings);
    }


    // --- Endpoints de Atividades ---
    @PostMapping("/{eventId}/activities")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZER') and @eventService.getOrganizerIdByEventId(#eventId) == authentication.principal.id)")
    @Operation(summary = "Adicionar uma nova atividade a um evento", description = "Permite a um organizador adicionar uma atividade a um evento específico. O organizador deve ser o criador do evento.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "eventId", description = "ID do evento ao qual adicionar a atividade", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atividade adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da atividade inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ActivityDTO addActivity(@PathVariable Long eventId, @Valid @RequestBody ActivityDTO activityDTO) {
        activityDTO.setEventId(eventId);
        return activityService.create(activityDTO);
    }

    @GetMapping("/{eventId}/activities")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Listar atividades de um evento", description = "Lista todas as atividades associadas a um evento específico. Acesso liberado para todos os usuários autenticados.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "eventId", description = "ID do evento para listar atividades", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atividades retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public List<ActivityResponseDTO> findActivitiesByEvent(
            @PathVariable Long eventId) {
        return activityService.findByEventoId(eventId);
    }
}