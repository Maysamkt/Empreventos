package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.MaterialComplementarDTO;
import br.edu.ifgoiano.Empreventos.service.MaterialComplementarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MaterialComplementarController {

    @Autowired
    private MaterialComplementarService materialService;


    @PostMapping("/atividades/{atividadeId}/materiais")
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialComplementarDTO addMaterial(
            @PathVariable Long atividadeId,
            @Valid @RequestBody MaterialComplementarDTO materialDTO) {
        return materialService.create(atividadeId, materialDTO);
    }

    @GetMapping("/atividades/{atividadeId}/materiais")
    public List<MaterialComplementarDTO> findMateriaisPorAtividade(@PathVariable Long atividadeId) {
        return materialService.findByAtividadeId(atividadeId);
    }


    @DeleteMapping("/materiais/{materialId}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Long materialId) {
        materialService.delete(materialId);
        return ResponseEntity.noContent().build();
    }
}