package br.edu.ifgoiano.Empreventos.dto;

import br.edu.ifgoiano.Empreventos.util.MaterialType;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ComplementaryMaterialDTO {

    private Long id;

    private Long activityId;

    @NotBlank(message = "O título do material é obrigatório.")
    private String title;
    private String description;

    @NotBlank(message = "A URL do arquivo é obrigatória.")
    @URL(message = "A URL do arquivo deve ser válida.")
    private String url;
    private MaterialType materialType;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }
}