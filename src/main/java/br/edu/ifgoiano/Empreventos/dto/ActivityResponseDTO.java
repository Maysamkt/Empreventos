package br.edu.ifgoiano.Empreventos.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ActivityResponseDTO {
    private Long id;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<ComplementaryMaterialDTO> complementaryMaterials;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<ComplementaryMaterialDTO> getComplementaryMaterials() {
        return complementaryMaterials;
    }

    public void setComplementaryMaterials(List<ComplementaryMaterialDTO> complementaryMaterials) {
        this.complementaryMaterials = complementaryMaterials;
    }
}