package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.ActivityDTO;
import br.edu.ifgoiano.Empreventos.dto.ActivityResponseDTO;
import br.edu.ifgoiano.Empreventos.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    //Retorna a atividade com seus materiais
    @GetMapping("/{id}")
    public ActivityResponseDTO findById(@PathVariable Long id) {
        return activityService.findById(id);
    }

    @PutMapping("/{id}")
    public ActivityDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ActivityDTO atividadeDTO) {
        return activityService.update(id, atividadeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}