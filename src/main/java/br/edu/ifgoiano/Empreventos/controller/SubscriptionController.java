package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.SubscriptionDTO;
import br.edu.ifgoiano.Empreventos.dto.SubscriptionResponseDTO;
import br.edu.ifgoiano.Empreventos.service.EventService;
import br.edu.ifgoiano.Empreventos.service.SubscriptionService;
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
@RequestMapping("/api/subscriptions")
@Tag(name = "Inscrições", description = "Operações relacionadas a inscrições em eventos")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("hasRole('LISTENER')")
    @Operation(summary = "Criar uma nova inscrição", description = "Permite a um participante (LISTENER) se inscrever em um evento.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscrição criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: evento não encontrado, usuário já inscrito)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (apenas LISTENERs)")
    })
    public ResponseEntity<SubscriptionResponseDTO> create(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        var createdSubscription = subscriptionService.create(subscriptionDTO);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER') or (hasRole('LISTENER') and @subscriptionService.findById(#id).listenerId == authentication.principal.id)")
    @Operation(summary = "Buscar inscrição por ID", description = "Admin ou Organizador podem buscar qualquer inscrição; Participantes (LISTENERs) só podem buscar suas próprias.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "id", description = "ID da inscrição", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição encontrada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada")
    })
    public ResponseEntity<SubscriptionResponseDTO> findById(@PathVariable Long id) {
        var subscription = subscriptionService.findById(id);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER') or (hasRole('LISTENER') and @subscriptionService.findById(#id).listenerId == authentication.principal.id)")
    @Operation(summary = "Cancelar uma inscrição", description = "Admin ou Organizador podem cancelar qualquer inscrição; Participantes (LISTENERs) só podem cancelar suas próprias.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "id", description = "ID da inscrição a ser cancelada", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição cancelada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Inscrição não encontrada")
    })public ResponseEntity<SubscriptionResponseDTO> cancel(@PathVariable Long id) {
        var cancelledSubscription = subscriptionService.cancel(id);
        return ResponseEntity.ok(cancelledSubscription);
    }

    @GetMapping("/by-event/{eventId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZER') and @eventService.getOrganizerIdByEventId(#eventId) == authentication.principal.id)")
    @Operation(summary = "Listar inscrições por evento", description = "Admin pode listar todas as inscrições de qualquer evento; Organizadores podem listar inscrições de seus próprios eventos.")
    @SecurityRequirement(name = "bearerAuth")
    @Parameter(name = "eventId", description = "ID do evento", required = true, example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de inscrições retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public List<SubscriptionResponseDTO> findSubscriptionsForEvent(@PathVariable Long eventId) throws AccessDeniedException {
        return subscriptionService.findSubscriptionsByEventId(eventId);
    }
}