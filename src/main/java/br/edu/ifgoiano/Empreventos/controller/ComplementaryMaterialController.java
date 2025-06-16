package br.edu.ifgoiano.Empreventos.controller;

import br.edu.ifgoiano.Empreventos.dto.ComplementaryMaterialDTO;
import br.edu.ifgoiano.Empreventos.service.ComplementaryMaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities/{activityId}/materials")
public class ComplementaryMaterialController {

    @Autowired
    private ComplementaryMaterialService materialService;

    @PostMapping
    @PreAuthorize("hasRole('SPEAKER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ComplementaryMaterialDTO addMaterial(
            @PathVariable Long activityId,
            @Valid @RequestBody ComplementaryMaterialDTO materialDTO) {

        materialDTO.setActivityId(activityId);
        return materialService.create(materialDTO);
    }

    @GetMapping
    public List<ComplementaryMaterialDTO> findMateriaisPorAtividade(
            @PathVariable Long activityId) {
        return materialService.findByAtividadeId(activityId);
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Long materialId) {
        materialService.delete(materialId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<ComplementaryMaterialDTO> updateMaterial(
            @PathVariable Long materialId,
            @Valid @RequestBody ComplementaryMaterialDTO materialDTO) {

        var updatedMaterial = materialService.update(materialId, materialDTO);
        return ResponseEntity.ok(updatedMaterial);
    }
}