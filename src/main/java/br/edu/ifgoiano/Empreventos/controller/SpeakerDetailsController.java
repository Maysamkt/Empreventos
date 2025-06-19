package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.request.SpeakerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.SpeakerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.service.SpeakerDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/speakers")
public class SpeakerDetailsController {

    private final SpeakerDetailsService speakerDetailsService;

    public SpeakerDetailsController(SpeakerDetailsService speakerDetailsService) {
        this.speakerDetailsService = speakerDetailsService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
    public List<SpeakerDetailsResponseDTO> findAll() {
        return speakerDetailsService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER') or (hasRole('SPEAKER') and @speakerDetailsService.getUserIdBySpeakerDetailsId(#id) == authentication.principal.id)")
    public SpeakerDetailsResponseDTO findById(@PathVariable Long id) {
        return speakerDetailsService.findById(id);
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('SPEAKER') and #userId == authentication.principal.id)")
    public SpeakerDetailsResponseDTO create(@PathVariable Long userId,
                                            @Valid @RequestBody SpeakerDetailsRequestDTO speakerRequestDTO) {
        return speakerDetailsService.create(userId, speakerRequestDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('SPEAKER') and @speakerDetailsService.getUserIdBySpeakerDetailsId(#id) == authentication.principal.id)")
    public SpeakerDetailsResponseDTO update(@PathVariable Long id,
                                            @Valid @RequestBody SpeakerDetailsRequestDTO speakerRequestDTO) {
        return speakerDetailsService.update(id, speakerRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        speakerDetailsService.delete(id);
    }
}
