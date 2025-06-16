package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.request.OrganizerDetailsRequestDTO;
import br.edu.ifgoiano.Empreventos.dto.response.OrganizerDetailsResponseDTO;
import br.edu.ifgoiano.Empreventos.service.OrganizerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerDetailsController {

    @Autowired
    private OrganizerDetailsService organizerDetailsService;

    @GetMapping("/{id}")
    public OrganizerDetailsResponseDTO findbyuserId(@PathVariable Long userId)
    {
        return organizerDetailsService.findById(userId);
    }

    @PutMapping("/{id}")
    public OrganizerDetailsRequestDTO update (
            @PathVariable long userId,
            @Valid @RequestBody OrganizerDetailsRequestDTO organizerDetailsRequestDTO) {
                return organizerDetailsService.update(userId, organizerDetailsRequestDTO);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity <?> delete (@PathVariable long userId) {
        organizerDetailsService.delete(userId);
        return ResponseEntity.ok().build();
    }

}
