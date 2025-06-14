package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.ActivityResponseDTO;
import br.edu.ifgoiano.Empreventos.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private ActivityService activityService;

    //Retorna a atividade com seus materiais
    @GetMapping("/{id}")
    public ActivityResponseDTO findById(@PathVariable Integer id) {
        return activityService.findById(id);
    }

    /* // Outros endpoints para gerenciar a atividade
    @PutMapping("/{id}")
    public ActivityDTO update(
        // CORRIGIDO: Long para Integer
        @PathVariable Integer id,
        @Valid @RequestBody ActivityDTO atividadeDTO) {
        return activityService.update(id, atividadeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        // CORRIGIDO: Long para Integer
        @PathVariable Integer id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }
    */
}