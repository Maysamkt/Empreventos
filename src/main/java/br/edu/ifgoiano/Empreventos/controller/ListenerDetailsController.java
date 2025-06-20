package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.request.ListenerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.ListenerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.service.ListenerDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listeners")
public class ListenerDetailsController {

    private final ListenerDetailsService listenerDetailsService;

    public ListenerDetailsController(ListenerDetailsService listenerDetailsService) {
        this.listenerDetailsService = listenerDetailsService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public List<ListenerDetailsResponseDTO> findAll() {
        return listenerDetailsService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER') or (hasRole('LISTENER') and @listenerDetailsService.getUserIdByListenerDetailsId(#id) == authentication.principal.id)")
    public ListenerDetailsResponseDTO findById(@PathVariable long id) {
        return listenerDetailsService.findById(id);
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('LISTENER') and #userId == authentication.principal.id)")
    public ListenerDetailsResponseDTO create(@PathVariable long userId,
                                             @Valid @RequestBody ListenerDetailsRequestDTO listenerRequestDTO) {
        return listenerDetailsService.create(userId, listenerRequestDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER') or (hasRole('LISTENER') and @listenerDetailsService.getUserIdByListenerDetailsId(#id) == authentication.principal.id)")
    public ListenerDetailsResponseDTO update(@PathVariable Long id,
                                             @Valid @RequestBody ListenerDetailsRequestDTO listenerRequestDTO) {
        return listenerDetailsService.update(id, listenerRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable long id) {
        listenerDetailsService.delete(id);
    }
}
