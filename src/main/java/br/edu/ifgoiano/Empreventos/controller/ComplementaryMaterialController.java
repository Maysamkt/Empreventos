package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.ComplementaryMaterialDTO;
import br.edu.ifgoiano.Empreventos.service.MaterialComplementarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades/{activityId}/materiais")
public class MaterialComplementarController {

    @Autowired
    private MaterialComplementarService materialService;


    @PostMapping("/atividades/{atividadeId}/materiais")
    @ResponseStatus(HttpStatus.CREATED)
    public ComplementaryMaterialDTO addMaterial(
            @PathVariable Integer ActivityId,
            @Valid @RequestBody ComplementaryMaterialDTO materialDTO) {
        return materialService.create(ActivityId, materialDTO);
    }

    @GetMapping("/atividades/{atividadeId}/materiais")
    public List<ComplementaryMaterialDTO> findMateriaisPorAtividade(@PathVariable Integer ActivityId) {
        return materialService.findByAtividadeId(ActivityId);
    }


    @DeleteMapping("/materiais/{materialId}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Integer materialId) {
        materialService.delete(materialId);
        return ResponseEntity.noContent().build();
    }
}