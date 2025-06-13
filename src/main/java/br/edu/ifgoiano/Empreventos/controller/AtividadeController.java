package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.AtividadeResponseDTO;
import br.edu.ifgoiano.Empreventos.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    //Retorna a atividade com seus materiais
    @GetMapping("/{id}")
    public AtividadeResponseDTO findById(@PathVariable Long id) {
        return atividadeService.findById(id);
    }

    /* // Outros endpoints para gerenciar a atividade

    @PutMapping("/{id}")
    public AtividadeDTO update(@PathVariable Long id, @Valid @RequestBody AtividadeDTO atividadeDTO) {
        return atividadeService.update(id, atividadeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        atividadeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    */
}