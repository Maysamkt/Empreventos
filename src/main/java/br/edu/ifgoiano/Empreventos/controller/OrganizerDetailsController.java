package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.request.OrganizerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.OrganizerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.dto.response.SpeakerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.service.OrganizerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerDetailsController {

    @Autowired
    private OrganizerDetailsService organizerDetailsService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZER') and @organizerDetailsService.getUserIdByOrganizerDetailsId(#id) == authentication.principal.id)")
    public OrganizerDetailsResponseDTO findbyuserId(@PathVariable Long userId)
    {
        return organizerDetailsService.findById(userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('ORGANIZER') and @organizerDetailsService.getUserIdByOrganizerDetailsId(#id) == authentication.principal.id)")
    public OrganizerDetailsRequestDTO update (
            @PathVariable long userId,
            @Valid @RequestBody OrganizerDetailsRequestDTO organizerDetailsRequestDTO) {
                return organizerDetailsService.update(userId, organizerDetailsRequestDTO);
    }

    @DeleteMapping ("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <?> delete (@PathVariable long userId) {
        organizerDetailsService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrganizerDetailsResponseDTO> findAll() {
        return organizerDetailsService.findAll();
    }


}
